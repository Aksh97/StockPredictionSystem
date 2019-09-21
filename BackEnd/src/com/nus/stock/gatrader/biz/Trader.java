package com.nus.stock.gatrader.biz;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nus.stock.gatrader.algorithm.Algorithms;
import com.nus.stock.gatrader.algorithm.Simulation;
import com.nus.stock.gatrader.dt.DecisionTree;
import com.nus.stock.jersey.LoginRestService;
import com.nus.stock.util.Path;


public class Trader {
    public static final int GENERATIONS = 2000;
    public static final int POPULATION_SIZE = 50;
    public static final int INIT_TREE_SIZE = 30;
    private static Map<String, Decision2> cachedTickPredict = new HashMap<>();
    private static Map<String, List<Candle>> latestStockDataMap = new HashMap<>();
    
    public static final class Decision2 {
    	private String trend;
    	private double confidence;
    	
    	public Decision2(){}
    	public Decision2(String  d, double conf) {
    		trend = d;
    		confidence = conf;
    	}

		public String getTrend() {
			return trend;
		}

		public double getConfidence() {
			return confidence;
		}
    	
    }
    
    public Trader() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					ga_main_entry();
					try {
						Thread.sleep(1000*3600);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
 
    private static Candle getCandle(String line){
        Candle c = new Candle();
        String[] fields = line.split(",");
        try {
        	 c.setDatetime(fields[0])
        	 .setOpen(Double.parseDouble(fields[1]))
             .setHigh(Double.parseDouble(fields[2]))
             .setLow(Double.parseDouble(fields[3]))
             .setClose(Double.parseDouble(fields[4]))
             .setVolume((int)Double.parseDouble(fields[5]));
             boolean [] features = new boolean[18];
             for(int i = 6, j = 0; i < fields.length; i++,j++){
                 features[j] = Boolean.parseBoolean(fields[i]);
             }
             c.indicatorList = features;
//             System.out.println(c.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
       
        return c;
    }
    
    public static void main(String[] args){
    	ga_main_entry();
    }
    
    //return stock tomorrow trend signal. 0:down; 1:up
    public Decision2 predictStockTrend(String symbol) {
		return cachedTickPredict.get(symbol);
	}
    
    public List<String> getHistoryData(String symbol) {
		List<String> retList = new ArrayList<>();
		
		return retList;
	}
    
    private static void getStockData() {
    	long t = System.currentTimeMillis();
    	Process process;
    	String argString[]={"python",""};
    	argString[1] = LoginRestService.curRootPathString+"/test/stockDataMining.py";
    	String cmd = "python "+ Path.PRJ_BASE_PATH + "test/stockDataMining.py";
    	try{
    		System.out.println("start");
    		process = Runtime.getRuntime().exec(cmd);
    		BufferedInputStream in = new BufferedInputStream(process.getInputStream());
    		BufferedInputStream err = new BufferedInputStream(process.getErrorStream());

    		BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
    		BufferedReader errBr = new BufferedReader(new InputStreamReader(err));
    		String lineStr;
    		while ((lineStr = inBr.readLine()) != null) {
    			System.out.println(lineStr);
    		}
    		while ((lineStr = errBr.readLine()) != null) {
    			System.out.println(lineStr);
    		}        
    		int exitValue = process.waitFor();

    		inBr.close();
    		errBr.close();
    		in.close();
    		err.close();
    		long t2=System.currentTimeMillis();
    		System.out.println("start over,cost:"+(t2-t)/1000+"s");

    	}
    	catch (IOException e){
    		e.printStackTrace();
    	} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
    
    private static void ga_main_entry(){
    	getStockData(); //TODO
    	
        List<List<Candle>> stockdata = new ArrayList<>();
        
        String folderpath = Path.PRJ_BASE_PATH + "data/";
        File folder = new File(folderpath);
        System.out.println(folder.getAbsolutePath());
       
        File[] list = folder.listFiles();
        int trainingSize;
        int testingSize;
        
        for(File file: list){        
        	System.out.println("mwz..."+file.getAbsolutePath());

            String symbol = file.getName().replace(".csv","");
            //System.out.println(symbol);
            ArrayList<Candle> stock = new ArrayList<>();
            try {
            	List<String> lineStrings = Files.readAllLines(Paths.get(folderpath+file.getName()));
            	lineStrings.remove(0);
            	Candle tCandle = null;
            	int len = lineStrings.size();
            	trainingSize = len * 2 / 3;
            	testingSize = len - trainingSize;
            	System.out.println("trainingSize "+trainingSize+" testingSize "+testingSize);
            	for (int i = 0; i < trainingSize; i++) {
            		String line = (String) lineStrings.get(i);
					Candle c = getCandle(line);
                    stock.add(c);
                    tCandle = c;
            	}
            	List<Candle> cacheTestDataCandles = new ArrayList<>();
            	for (int i = trainingSize; i < len; i++) {
            		String line = (String) lineStrings.get(i);
					Candle c = getCandle(line);
                    stock.add(c);
                    tCandle = c;
                    cacheTestDataCandles.add(c);
            	}
            	latestStockDataMap.put(symbol, cacheTestDataCandles);
                stockdata.add(stock);

			} catch (Exception e) {
				e.printStackTrace();
			}
            
            stockdata.add(stock);
        }
        //stockdata.forEach(stock->System.out.println(stock.size()));
        List<Double> bestFitness = new ArrayList<>();
        List<Double> bestFitness2 = new ArrayList<>();
        List<Double> bestFitness3 = new ArrayList<>();
        List<Genome> population = new ArrayList<>();
        Algorithms.initialize(population, POPULATION_SIZE, INIT_TREE_SIZE);
        
        // SIMULATE
        simulate(population, stockdata);
        
        int generation = 1;
        //Find the most suitable DT through GA
        while(generation <= GENERATIONS){
            List<Genome> select = Algorithms.select(population, POPULATION_SIZE/2, generation);
            List<Genome> crossoverOffspring = Algorithms.crossover(select, generation);
            simulate(crossoverOffspring, stockdata);
            
            List<Genome> mutationOffspring = Algorithms.mutate(crossoverOffspring, generation);
            simulate(mutationOffspring, stockdata);
            
            List<Genome> union = new ArrayList<>();
            union.addAll(population);
            union.addAll(crossoverOffspring);
            union.addAll(mutationOffspring);
            
            population = Algorithms.select(union, POPULATION_SIZE, generation);
            bestFitness.add(population.get(0).getFitness());
            bestFitness2.add(population.get(1).getFitness());
            bestFitness3.add(population.get(2).getFitness());
            //System.out.println("#### GENERATION "+(generation)+" BEST FITNESS = "+ population.get(0).getFitness());
            generation++;
        }
        
        System.out.println(bestFitness);
        System.out.println(bestFitness2);
        System.out.println(bestFitness3);
        
        //evaluate tickers tomorrow's trend through DT        
        DecisionTree tree = population.get(0).getDecisionTree();
        Map<String, String> map = new HashMap<String, String>();
        double yesterdayPrice = -1;
        double testAccuracy = .0f;
        for (Entry<String, List<Candle>> s : latestStockDataMap.entrySet()) {
        	String symbol = s.getKey();
        	List<Candle> candle = s.getValue();
        	int l = candle.size();
        	Decision d = tree.evaluate(candle.get(l-1));
        	DecisionTree tree2 = population.get(1).getDecisionTree();
        	Decision d2 = tree.evaluate(candle.get(l-1));
        	DecisionTree tree3 = population.get(2).getDecisionTree();
        	Decision d3 = tree.evaluate(candle.get(l-1));
        	System.out.println("for "+symbol+",dt:"+d+",dt2:"+d2+",dt3:"+d3);
        	Decision tDecision;
        	//calculate testAccuracy
            int truePredict = 0;
        	for (int i = 1; i < l; i++) { //skip first data for convenience
        		Decision dd = tree.evaluate(candle.get(i));
        		double t = candle.get(i).getClose() - candle.get(i-1).getClose();
        		if ((t < -0.001//threshold
        				&& dd == Decision.SELL) ||
        			(t > 0.001 && dd == Decision.BUY)) {
        			truePredict++;
        		}
        	}
        	System.out.println("["+symbol+"]"+" testAccuracy:"+(truePredict*100.0/l)+"%");
        	d.setConfidence(truePredict*1.0/l);
        	Decision2 dd = new Decision2(d==Decision.BUY?"up":"down", truePredict*1.0/l);
        	cachedTickPredict.put(symbol, dd);
        }

    }
    
    private static void simulate(List<Genome> population, List<List<Candle>> stockdata){
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(population.size());      
        for(Genome g : population){
            Simulation sim=new Simulation(g, stockdata, latch);
            executor.execute(sim);
        }
        try {
            latch.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(Trader.class.getName()).log(Level.SEVERE, null, ex);
        }
        executor.shutdown();

    }
}

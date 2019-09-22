package com.nus.stock.service;

import java.util.List;

import com.nus.stock.bean.StockSymbol;

public interface StockSymbolService {
	
	public List<StockSymbol> findAllStock();

}

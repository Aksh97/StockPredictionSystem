package com.nus.stock.service;

import java.util.List;

import com.nus.stock.bean.StockSymbol;
import com.nus.stock.dao.basic.AbstractServiceDao;

public class DaoStockSymbolService extends AbstractServiceDao implements StockSymbolService{

	@SuppressWarnings("unchecked")
	@Override
	public List<StockSymbol> findAllStock() {
		List<?> symbolList = (List<?>)getDao().query("getAllStockSymbols");
		return symbolList == null || symbolList.isEmpty() ? null : (List<StockSymbol>)symbolList;
	}

}

package com.empirestateids.dao;

import java.util.List;

import com.empirestateids.domain.Lookup;

public interface MiscLookupMapper {
    
    List<Lookup> getGroups();
    
    List<Lookup> getLatestArticles();

	List<Lookup> getLatestGalleries();
    
	List<Lookup> getFederations();
}
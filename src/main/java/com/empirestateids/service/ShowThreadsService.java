/**
 * 
 */
package com.empirestateids.service;

import java.util.List;
/**
 * @author Charlie D
 *
 */
public interface ShowThreadsService {
	
	public String toString();
	
	public String getMemoryStats() throws Exception;
	
	public List<Thread> getThreadList() throws Exception;
		
}

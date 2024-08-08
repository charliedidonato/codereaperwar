/**
 * 
 */
package com.empirestateids.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.empirestateids.utls.UtilityMethods;

/**
 * @author Charlie D
 *
 */
@Component
@Service("ShowThreadsService")
@Scope("prototype")
public class ShowThreadsServiceImpl implements ShowThreadsService {
	
	static Logger logger = LogManager.getLogger("ShowThreadsServiceImpl");
	
	private static final DecimalFormat df = new DecimalFormat("###,###,###,###");
    private static final String eol = "<br>";
    
	public ShowThreadsServiceImpl(){
		
	}
	
	@Override
	public String toString(){
		StringBuffer s = new StringBuffer();
		try {
		    s.append(this.getMemoryStats());
		    List<Thread> a = this.getThreadList();
		    int i = 1;
		    for (Thread thread: a){  	
		    	if (i%2==0){
		    		s.append(" ID:"+thread.getId()+" Name:"+thread.getName()+eol);
		    	}else {
		    	    s.append("ID:"+thread.getId()+" Name:"+thread.getName());
		    	}
		    	i++;
		    }
		}catch (Exception e){
			logger.error("Exception:" + e.getMessage() + " Trace:" +	UtilityMethods.getStackTrace(e));
		}
		return (s.toString());
	}

	public String getMemoryStats() throws Exception {
		StringBuffer s = new StringBuffer();
		try {
			Runtime rt = Runtime.getRuntime();
			long totalMemory = rt.totalMemory();
			long maxMemory = rt.maxMemory();
			long freeMemory = rt.freeMemory();
			s.append("Total Memory:" + df.format(totalMemory));
			s.append(" Max Memory:" + df.format(maxMemory));
			s.append(" Free Memory:" + df.format(freeMemory)+eol);
		} catch (Exception e) {

		}
		return (s.toString());
	}
    
    public List<Thread> getThreadList(){
    	List <Thread> a = new ArrayList<Thread>();
    	ThreadGroup group =
                Thread.currentThread().getThreadGroup();

        ThreadGroup topGroup = group;

        // traverse the ThreadGroup tree to the top
        while ( group != null ) {
            topGroup = group;
            group = group.getParent();
        }

        // Create a destination array that is about
        // twice as big as needed to be very confident
        // that none are clipped.
        int estimatedSize = topGroup.activeCount() * 2;
        Thread[] slackList = new Thread[estimatedSize];

        // Load the thread references into the oversized
        // array. The actual number of threads loaded
        // is returned.
        int actualSize = topGroup.enumerate(slackList);

        // copy into a list that is the exact size
        Thread[] list = new Thread[actualSize];
        System.arraycopy(slackList, 0, list, 0, actualSize);
        a = Arrays.asList(list);
    	return a;
    }

}

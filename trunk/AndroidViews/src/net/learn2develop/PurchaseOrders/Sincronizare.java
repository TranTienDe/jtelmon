

package net.learn2develop.PurchaseOrders;

import net.learn2develop.R;
import android.app.Activity;
import android.os.Bundle;
import android.app.ListActivity; 
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Vector;
import android.app.Activity;
import android.os.Bundle;
import android.util.Xml; 
import android.widget.ArrayAdapter;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.InputStream;
import org.json.JSONArray;
import java.io.BufferedReader;
import android.util.Log;


public class Sincronizare extends ListActivity {
	
	private final static String SERVICE_URI = "http://192.168.61.3/SalesService/SalesService.svc";
	// private final static String SERVICE_URI = "http://192.168.101.222/SalesService/SalesService.svc";		
	
	private Data dm;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.listview);
        
        HttpGet request = new HttpGet(SERVICE_URI + "/json/getproducts"  );       
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
 
        DefaultHttpClient httpClient = new DefaultHttpClient();

      
        String theString = new String("");
        
        HttpGet request1 = new HttpGet(SERVICE_URI +   "/json/getroutes/3165");       
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
 
        DefaultHttpClient httpClient1 = new DefaultHttpClient();

       
        
        try {
        	HttpResponse response = httpClient.execute(request);
        	HttpEntity responseEntity = response.getEntity();
        	InputStream stream = responseEntity.getContent();
        	BufferedReader reader = new BufferedReader(
					new InputStreamReader(stream));
           
        	Vector<String> vectorOfStrings = new Vector<String>();
        	String tempString       = new String();
        	String tempStringID     = new String();
        	String tempStringName   = new String();
        	String tempStringPrice  = new String();
        	String tempStringSymbol = new String();
        	
        	
        	
        	
        	StringBuilder builder = new StringBuilder();
        	String line;
        	while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
        	
        	
        	stream.close();
        	theString = builder.toString();
        	
        	
        	JSONObject json=new JSONObject(theString);
        	Log.i("_GetPerson_","<jsonobject>\n"+json.toString()+"\n</jsonobject>");
        	
        	this.dm = new Data(this);
        	
        	JSONArray nameArray;
        	nameArray=json.getJSONArray("getProductsResult");
        	
        	 for(int i=0;i<nameArray.length();i++)
             {
             	tempStringID     = nameArray.getJSONObject(i).getString("ID");
             	tempStringName   = nameArray.getJSONObject(i).getString("Name");
             	tempStringPrice  = nameArray.getJSONObject(i).getString("Price");
             	tempStringSymbol = nameArray.getJSONObject(i).getString("Symbol");
             	
             
     			
     			this.dm.insertIntoProducts(tempStringID,tempStringName,tempStringPrice,tempStringSymbol);
     			
     			
     			
     			tempString=nameArray.getJSONObject(i).getString("Name")+"\n"+
                 		nameArray.getJSONObject(i).getString("Price")+"\n"+nameArray.getJSONObject(i).getString("Symbol");
                 vectorOfStrings.add(new String(tempString));
             }
        	 
        	 int orderCount = vectorOfStrings.size();
             String[] orderTimeStamps = new String[orderCount];
             vectorOfStrings.copyInto(orderTimeStamps); 
           //  setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , orderTimeStamps));
        	
        } catch (Exception e) {
			e.printStackTrace();
		}  
       
             
        	
        	try {
        		HttpResponse response1 = httpClient1.execute(request1);
            	HttpEntity response1Entity = response1.getEntity();
            	InputStream stream1 = response1Entity.getContent();
            	BufferedReader reader1 = new BufferedReader(
    					new InputStreamReader(stream1));
            	
            	Vector<String> vectorOfStrings = new Vector<String>();
            	String tempString1       = new String();
            	String tempStringAgent     = new String();
            	String tempStringClient   = new String();
            	String tempStringRoute  = new String();
            	String tempStringZone = new String();
            	
            	StringBuilder builder1 = new StringBuilder();
            	String line1;
            	while ((line1 = reader1.readLine()) != null) {
    				builder1.append(line1);
    			}
            	
            	stream1.close();
            	theString = builder1.toString();
            	
            	
            	JSONObject json1=new JSONObject(theString);
            	Log.i("_GetPerson_","<jsonobject>\n"+json1.toString()+"\n</jsonobject>");
            	
            	this.dm = new Data(this);
            	
            	JSONArray nameArray1;
            	nameArray1=json1.getJSONArray("GetRoutesByAgentResult");
            	
            	 for(int i=0;i<nameArray1.length();i++)
                 {
                 	tempStringAgent     = nameArray1.getJSONObject(i).getString("Agent");
                 	tempStringClient   = nameArray1.getJSONObject(i).getString("Client");
                 	tempStringRoute  = nameArray1.getJSONObject(i).getString("Route");
                 	tempStringZone = nameArray1.getJSONObject(i).getString("Zone");
                 		
         			
         			this.dm.insertIntoClients(tempStringAgent,tempStringClient,tempStringRoute,tempStringZone);
         			
                     tempString1=nameArray1.getJSONObject(i).getString("Client")+"\n"+
                     		nameArray1.getJSONObject(i).getString("Route")+"\n"+nameArray1.getJSONObject(i).getString("Zone");
                     vectorOfStrings.add(new String(tempString1));
                 }
            	                  
           
    		
            int orderCount1 = vectorOfStrings.size();
            String[] orderTimeStamps1 = new String[orderCount1];
            vectorOfStrings.copyInto(orderTimeStamps1); 
            // setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , orderTimeStamps1));
            
		
        	
        } catch (Exception a) {
        	a.printStackTrace();
        }
        
    }
    
    
    
    // @Override
	protected void onDestroy() {
		super.onDestroy();
		if (dm != null) {
			dm.close();
		}
	}
    
	
	
}


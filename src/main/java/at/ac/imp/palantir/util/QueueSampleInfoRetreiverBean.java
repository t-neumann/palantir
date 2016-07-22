package at.ac.imp.palantir.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;

import org.primefaces.json.JSONObject;
import org.primefaces.json.JSONTokener;

import at.ac.imp.palantir.model.QueueSampleMetaInfo;

@Stateless
@Remote(QueueSampleInfoRetreiver.class)
public class QueueSampleInfoRetreiverBean implements QueueSampleInfoRetreiver {
	
	// Queue fields
	private static final int FLOWCELL_FIELD = 0;
	private static final int SEQUENCER_FIELD = 2;
	private static final int VENDOR_FIELD = 3;
	private static final int READTYPE_FIELD = 8;
	private static final int LANE_FIELD = 10;
	private static final int SAMPLE_ID_FIELD = 11;
	private static final int USER_FIELD = 12;
	private static final int ORGANISM_FIELD = 17;
	private static final int CELLTYPE_FIELD = 18;
	private static final int EXPERIMENTTYPE_FIELD = 19;
	private static final int GENOTYPE_FIELD = 20;
	private static final int ANTIBODY_FIELD = 21;
	private static final int PRIMER_FIELD = 25;
	
	private static final int STATUS_FIELD = 48;
	
	// TaxIDs
	private static final String HUMAN_TAX_ID = "9606";
	private static final String MOUSE_TAX_ID = "39442";
	
	// Accepted STATI
	private static final String ACCEPTED_STATUS = "use:ok";
	
	private String user = "Tobias.Neumann";
	private String password = "Osal7Onu39";
	private String group = "zuber";
	
	private QueueSampleMetaInfo urlHandler(int sampleId) {
		
		QueueSampleMetaInfo result = null;
				
		URL url;
		try {
			url = new URL("http://gecko:9100/sequencedSamples/group/" + this.group);
			URLConnection uc = url.openConnection();

			String userpass = user + ":" + password;
			String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());

			uc.setRequestProperty ("Authorization", basicAuth);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			
			String line = null;
			
			String[] sampleLine = null;
			
			while((line = in.readLine()) != null) {
				String[] fields = handleLine(line,sampleId);
				if (fields != null) {
					sampleLine = handleLine(line,sampleId);
				}
			}
			
			if (sampleLine != null) {
				result = parseFieldsToObject(sampleLine);
			}
			
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	private String[] handleLine(String line, Integer sampleId) {
		String[] fields = line.split("\t");
		
		if (fields[QueueSampleInfoRetreiverBean.SAMPLE_ID_FIELD].equals(sampleId.toString()) &&
				fields[QueueSampleInfoRetreiverBean.STATUS_FIELD].equals(QueueSampleInfoRetreiverBean.ACCEPTED_STATUS)) {
			return fields;
		}
		
		return null;
	}
	
	private QueueSampleMetaInfo parseFieldsToObject(String[] fields) {
		
		String sequencer = fields[QueueSampleInfoRetreiverBean.SEQUENCER_FIELD];
		String flowcell = fields[QueueSampleInfoRetreiverBean.FLOWCELL_FIELD];
		String vendor = fields[QueueSampleInfoRetreiverBean.VENDOR_FIELD];
		String readType = fields[QueueSampleInfoRetreiverBean.READTYPE_FIELD];
		int lane = Integer.parseInt(fields[QueueSampleInfoRetreiverBean.LANE_FIELD]);
		int sampleId = Integer.parseInt(fields[QueueSampleInfoRetreiverBean.SAMPLE_ID_FIELD]);
		String user = fields[QueueSampleInfoRetreiverBean.USER_FIELD];
		String organism = fields[QueueSampleInfoRetreiverBean.ORGANISM_FIELD];
		String celltype = fields[QueueSampleInfoRetreiverBean.CELLTYPE_FIELD];
		String experimentType = fields[QueueSampleInfoRetreiverBean.EXPERIMENTTYPE_FIELD];
		String genotype = fields[QueueSampleInfoRetreiverBean.GENOTYPE_FIELD];
		String antibody = fields[QueueSampleInfoRetreiverBean.ANTIBODY_FIELD];
		String primer = fields[QueueSampleInfoRetreiverBean.PRIMER_FIELD];
		
		if (organism.equals(QueueSampleInfoRetreiverBean.HUMAN_TAX_ID)) {
			organism = "human";
		} else if (organism.equals(QueueSampleInfoRetreiverBean.MOUSE_TAX_ID)) {
			organism = "mouse";
		}
		
		return new QueueSampleMetaInfo(sampleId, sequencer, vendor, flowcell, readType, lane, user, organism, celltype, experimentType, genotype, antibody, primer);
	}
	
	// New VBCF URL API
	// http://ngs.csf.ac.at/forskalle/apidoc
	// Retreive cookie 
	private String getVBCFCookie() {
		String cookie = null;
		HttpURLConnection c = null;
		try {
			Map<String, Object> params = new LinkedHashMap<>();
			params.put("username", user);
			params.put("password", password);

			StringBuilder postData = new StringBuilder();
			for (Map.Entry<String, Object> param : params.entrySet()) {
				if (postData.length() != 0)
					postData.append('&');
				postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				postData.append('=');
				postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
			}
			byte[] postDataBytes = postData.toString().getBytes("UTF-8");

			URL u = new URL("http://ngs.vbcf.ac.at/forskalle/api/login");
			c = (HttpURLConnection) u.openConnection();
			c.setRequestMethod("POST");
			c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			c.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
			c.setDoOutput(true);
			c.getOutputStream().write(postDataBytes);

			String cookieField = c.getHeaderField("Set-Cookie");

			cookieField = cookieField.substring(0, cookieField.indexOf(";"));
			String cookieName = cookieField.substring(0, cookieField.indexOf("="));
			String cookieValue = cookieField.substring(cookieField.indexOf("=") + 1, cookieField.length());

			cookie = cookieName + "=" + cookieValue;

			c.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			c.disconnect();
		}
		return cookie;
	}
	
	private String getVBCFJSONproperty(String sampleID, String key) {
		String value = null;
		HttpURLConnection c = null;
			try {
				String url = "http://ngs.vbcf.ac.at/forskalle/api/samples/" + sampleID;
				URL u = new URL(url);
				c = (HttpURLConnection) u.openConnection();
				
				String cookie = getVBCFCookie();
				c.setRequestProperty("Cookie", cookie);
				c.connect();
				
				BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
	            StringBuilder sb = new StringBuilder();
	            String line = "";
	            while ((line = br.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	            br.close();
	            
	            JSONObject json = new JSONObject(new JSONTokener(sb.toString()));
	            
	            value = json.getString(key);				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				c.disconnect();
			}
			return value;
	}

	@Override
	public QueueSampleMetaInfo getInfoForSample(int sampleId) {
		QueueSampleMetaInfo info = urlHandler(sampleId);
		info.setDescription(getVBCFJSONproperty(String.valueOf(sampleId), "descr"));
		return info;
	}
}
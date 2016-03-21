package at.ac.imp.palantir.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless
@Remote(QueueSampleInfoRetreiver.class)
public class QueueSampleInfoRetreiverBean implements QueueSampleInfoRetreiver {
	
	private static final int SAMPLE_ID_FIELD = 11;
	
	private String user = "Tobias.Neumann";
	private String password = "Osal7Onu39";
	private String group = "zuber";
	
	private String urlHandler(int sampleId) {
		
		String result = null;
				
		URL url;
		try {
			url = new URL("http://gecko:9100/sequencedSamples/group/" + this.group);
			URLConnection uc = url.openConnection();

			String userpass = user + ":" + password;
			String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());

			uc.setRequestProperty ("Authorization", basicAuth);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			
			String line = null;
			
			while((line = in.readLine()) != null) {
				String query = handleLine(line,sampleId);
				if (query != null) {
					result = query;
				}
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
	
	private String handleLine(String line, Integer sampleId) {
		String info = null;
		String[] fields = line.split("\t");
		if (fields[QueueSampleInfoRetreiverBean.SAMPLE_ID_FIELD].equals(sampleId.toString())) {
			//info = line;
			info = fields[QueueSampleInfoRetreiverBean.SAMPLE_ID_FIELD + 1];
		}
		return info;
	}

	@Override
	public String getInfoForSample(int sampleId) {
		String info = urlHandler(sampleId);
		return info;
	}
}
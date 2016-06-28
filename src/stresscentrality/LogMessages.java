package stresscentrality;

import peersim.cdsim.CDState;
import peersim.config.Configuration;
import peersim.core.Control;
import peersim.core.Network;
import peersim.core.Node;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class LogMessages implements Control {

	private static final String FILE_PATH = "filepath";
	private final String pathFile ;

	public LogMessages(String prefix){
        pathFile = Configuration.getString(prefix+"."+FILE_PATH);
        SimpleDateFormat time_formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String current_time_str = time_formatter.format(System.currentTimeMillis());
        writeIntoFile("\n"+ current_time_str+" - ");

	}

	public boolean execute() {

		//long cycle = 0L;
		long numNosp=0L;
		long numControl=0L;
		for(int n =0; n < Network.size(); n++) {
            Node v = Network.get(n);
			ReportPhase rp = ((ReportPhase) v.getProtocol(2));
			CountPhase cp = ((CountPhase) v.getProtocol(1));
			numNosp += cp.numNOSP;
			numControl += rp.numReport;
			//cycle = cp.cycle;
		}
		long tot = numNosp+numControl;
		String log = "("+ CDState.getCycle()+","+tot+ "; nosp:"+numNosp+" Control:"+numControl+")";

		writeIntoFile(log);

		return false;
	}

	public void writeIntoFile( String s){
        BufferedWriter bw = null;
        FileWriter fw = null;
		try {
            File file = new File(pathFile);
            fw = new FileWriter(file,true);
            bw = new BufferedWriter(fw);
            bw.write(s);
            bw.close();
		} catch (IOException e) {
			System.err.println("Problem writing to the file "+pathFile);
		}
	}

}


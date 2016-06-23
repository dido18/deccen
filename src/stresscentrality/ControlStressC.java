package stresscentrality;

import peersim.config.Configuration;
import peersim.core.Control;
import peersim.core.Network;
import peersim.core.Node;

import java.io.*;
import java.text.SimpleDateFormat;

public class ControlStressC implements Control {

	private static final String FILE_PATH = "filepath";
	private final String pathFile ;

	public ControlStressC(String prefix){
        pathFile = Configuration.getString(prefix+"."+FILE_PATH);
		SimpleDateFormat time_formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String current_time_str = time_formatter.format(System.currentTimeMillis());
		writeIntoFile("\n"+ current_time_str+" - ");

	}

	public boolean execute() {

		long sumSCs=0; ///sum of the
		long cycle = 0L;
		for(int n =0; n < Network.size(); n++) {
            Node v = Network.get(n);
			ReportPhase rp = ((ReportPhase) v.getProtocol(2));
			CountPhase cp = ((CountPhase) v.getProtocol(1));
            long tempTotal = 0;
			for (int i = 0; i < Network.size(); i++) {
				for (int j = 0; j < Network.size(); j++) {
                    Node s = Network.get(i);
                    Node t = Network.get(j);
					if (rp.tableReport.contains(s,t)){
                        int sv =  cp.spTable.get(s);
                        int vt =  cp.spTable.get(t);
						tempTotal += sv*vt;
                        }
				}
			}
			sumSCs +=tempTotal;
			cycle = cp.cycle;
		}
		String log = "("+cycle+","+sumSCs+")";

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
			System.err.println("Problem writing to the file"+pathFile);
		}
	}

}


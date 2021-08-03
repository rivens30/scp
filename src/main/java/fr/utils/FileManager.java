package fr.utils;

import java.io.*;

public class FileManager {
    private String SCP_path;
    private String SCP_FR_path;

    public FileManager(){
        SCP_path = System.getProperty("user.dir") + File.separator + "SCP" + File.separator;
        SCP_FR_path = System.getProperty("user.dir") + File.separator + "SCP-FR" + File.separator;
    }

    public String reader(String path) {
        String to_ret = "";
        try {
            File fileDir = new File(path);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF8"));

            String str;

            while ((str = in.readLine()) != null) {
                to_ret+= str + "\n";
            }

            in.close();
        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return to_ret;
    }

    public String[] splitStringEvery(String s, int interval) {
        int arrayLength = (int) Math.ceil(((s.length() / (double)interval)));
        String[] result = new String[arrayLength];

        int j = 0;
        int lastIndex = result.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            result[i] = s.substring(j, j + interval);
            j += interval;
        } //Add the last bit
        result[lastIndex] = s.substring(j);

        return result;
    }

    public String[] get_SCP(Boolean type, int scp) {
        String sc;

        if(scp < 10){
            sc = "00" + String.valueOf(scp);
        }else if(scp < 100){
            sc = "0" + String.valueOf(scp);
        }else{
            sc = String.valueOf(scp);
        }

        String target;
        if (type) {
            target = this.SCP_path + "SCP-" + sc + File.separator + "SCP-" + sc + ".txt";
        } else if (!(type)) {
            target = this.SCP_FR_path + "SCP-" + sc + "FR" + File.separator + "SCP-" + sc + "FR.txt";
        }else{
            return null;
        }
        System.out.println(target);
        if(new File(target).isFile()) {
            return splitStringEvery(reader(target), 2000);
        }else{
            return null;
        }
    }
}

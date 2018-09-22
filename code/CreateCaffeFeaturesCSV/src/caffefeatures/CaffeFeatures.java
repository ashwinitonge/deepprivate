/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caffefeatures;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Ashwini
 */
public class CaffeFeatures {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try
        {
            if (args.length < 3){
                System.out.println("Please provide feature_file filenames privacy_settings.");
                return;
            }
            String PrivacyFile = args[2];
            
            HashMap<String, String> PrivacySettings = new HashMap<>();         
            LoadPrivacySetting(PrivacyFile, PrivacySettings);   

            String FilenamesTXT = args[1];
            List<String> Filenames = new ArrayList<>();
            LoadFilenames(Filenames, FilenamesTXT);
            
            String FeatureFile = args[0];
            WriteCsv(FeatureFile, Filenames, PrivacySettings);

            
        }
        catch (Exception Ex)
        {
            //System.out.println(Ex);
            Ex.printStackTrace();
        }
    }
    
    static void LoadPrivacySetting(String Filename, HashMap<String, String> PrivacySettings) throws FileNotFoundException, IOException
    {
        BufferedReader Reader = new BufferedReader(new FileReader(new File(Filename)));
        String Line = "";
        
        while ((Line = Reader.readLine()) != null)
        {
            String Split[] = Line.split(",");
            
            PrivacySettings.put(Split[0], Split[1]);
        }
        
        Reader.close();
    }  
    
    static void LoadFilenames(List<String> Filenames, String FileN) throws FileNotFoundException, IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(new File(FileN)));
        
        String Line = "";
        reader.readLine();
        while ((Line = reader.readLine()) != null)
        {
            String Linesplit[] = Line.split(",");
            //Filenames.add(Line.trim());
            Filenames.add(Linesplit[0].trim());
        }
        
        reader.close();
    }
    
    static void WriteCsv(String Filename, List<String> Filenames, HashMap<String, String> PrivacySetting) throws FileNotFoundException, IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(new File(Filename)));
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(Filename.replace(".txt", ".csv"))));
        String Line = "";
        
        Line = reader.readLine();
        String FeaturesSplit[] = Line.split(" |,");
        int NumberOfFeatures = FeaturesSplit.length - 1;
        
        reader.close();
        
        reader = new BufferedReader(new FileReader(new File(Filename)));
        
        Line = "Filename";
        
        for (int i = 0; i < NumberOfFeatures; i++)
        {
            Line += "," + "F" + (i + 1);
            //writer.write("," + "F" + (i + 1));
        }
        
        Line += ",PrivacySettings\n";
        //writer.write(",PrivacySettings\n");
        
        writer.write(Line);
        
        int itr = 0;
        
        while ((Line = reader.readLine()) != null)
        {

            String Split[] = Line.split(" |,");
         
            if (itr >= Filenames.size())
            {
                break;
            }
            
            Line = Filenames.get(itr);
            
            if (Split.length < NumberOfFeatures)
            {
                continue;
            }
            
            for (int i = 1; i < Split.length; i++)
            {
                Line += "," + Split[i].trim();
                
            }
            
            Line += "," + PrivacySetting.get(Filenames.get(itr)) + "\n";
            
            
            writer.write(Line);
            System.out.println(Filenames.get(itr) + "," + itr);
            
            itr++;
        }
        
        writer.close();
        reader.close();
    }
    
    static void GenFeatureFigTxt(String Filename)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(new File(Filename)));
            
            String Line = "";
            
            Line = reader.readLine();
            
            String Splits[] = Line.split(",");
            reader.close();
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(Filename)));
            
            for (int i = 0; i < Splits.length; i++)
            {
                writer.write(Splits[i] + "\n");
            }
            
            writer.close();
        }
        catch(Exception Ex)
        {
            
        }
    }
}

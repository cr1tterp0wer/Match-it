package MetaDataReader;

import java.io.*;
import java.util.Scanner;

public class MetaDataReader implements IMetaDataReader {

	private TextureDataObject[] TextureDataArray;
	private FileReader reader;
	private BufferedReader bReader;
	private File file;
	private String description, location, type, dummy;
	private String[] splitArray;
	
	public MetaDataReader()
	{
		TextureDataArray = new TextureDataObject[100];
		Scanner streamReader = null;
	}
	
	///<summary>
	/// Takes in the file location and parses it; *Note* The First line is for file description,
	/// it doesn't get parsed.
	///<summary>
	public void readData(String _metaDataLocation) throws IOException
	{
      try
	  {
	    file = new File(_metaDataLocation);
        reader = new FileReader(file);
	    bReader = new BufferedReader(reader);
	    description = null;
	    type = null;
	    location = null;
	    
	    splitArray = new String[3];
	    dummy = "EMPTY_VALUE";
	    
	    //clear the first line
	    dummy = bReader.readLine();
	    assert( dummy != null);
	   
	    while(( dummy = bReader.readLine() ) != null )
	    {
	    	splitArray = dummy.split(", ");
	      
	      description = splitArray[0];
		  location = splitArray[1];
		  type = splitArray[2];
		  
		  add(type, description, location);
		
	    }
	  }catch(Exception e){}
	  finally 
      { 
		 if(dummy != null)
		 {
	     bReader.close();
		 splitArray = null;
		 dummy = null;
		 }
	   }
	}
	
	///<summary>
	/// Removes a TextureDataObject from the array
	///<summary>
	public boolean removeAt(int _index)
	{	TextureDataArray[_index] = null;
		sort();
		return true;
	}
	
	///<summary>
	/// Adds a TextureDataObject to the array
	///<summary>
	public void add(String _type, String _description, String _location)
	{
		for(int i =0; i < TextureDataArray.length; i++)
		{
			if(TextureDataArray[i] == null)
			{
				TextureDataArray[i] = new TextureDataObject(_type, _description, _location);
				break;
			}
		}
	}
	
	///<summary>
	/// Pushes all objects in the array to the lowest possible index
	///<summary>
	public void sort()
    {
     for (int i = 0; i < TextureDataArray.length; i++)
     {
      if (TextureDataArray[i] == null)
      {  
       for (int k = i+1; k < TextureDataArray.length; k++)
        {
         if (TextureDataArray[k] != null)
          {
        	TextureDataArray[i] = TextureDataArray[k];
          	TextureDataArray[k] = null;
          }
        }
       }
      }
    }
	
	///<summary>
	/// Lists all contents in a given Directory
	///<summary>
	public void listDirectory(String _directory)
	{
		File root = new File(_directory);
		File[] list = root.listFiles();
		for(int i = 0; i < list.length; i++)
		System.out.println(list[i].getAbsolutePath());
	}
	
	///<summary>
	/// Takes in the file location of all .zdx files in a directory
	/// and parses them into the TextureDataArray;
	///<summary>
	public void readDirectory(String _directory)
	{
		String str = "foo";
		String[] splitArray;
		
		File root = new File(_directory);
		File[] list = root.listFiles();
		for(int i = 0; i < list.length; i++)
		{
			if(list[i].isFile())
			{
			  str = list[i].getAbsolutePath();
			  splitArray = str.split("\\.");
			  
		       //if the file is a .zdx then add the components to the array
			  if(splitArray[1].toString().equalsIgnoreCase("zdx"))
			  {
				try{
				  readData(list[i].getAbsolutePath());
				  System.out.println("Absolute Path Passed");
				}catch (IOException e){}
			  }  
			 }
		}	
	}
	
	///<summary>
	///Listings
	///<summary>
	public void listTextureDataArray()
	{
		for(int i = 0; i < TextureDataArray.length; i++)
		{
			if(TextureDataArray[i] != null)
			{
			System.out.println(TextureDataArray[i].getDescription() + ", " + TextureDataArray[i].getLocation() + ", " + TextureDataArray[i].getType());
		
			}
		}
	}
	public void listDescriptionArray()
	{
		for(int i = 0; i < TextureDataArray.length; i++)
		{
			if(TextureDataArray[i] != null)
			{
			System.out.println(TextureDataArray[i].getDescription());
			}
		}
	}
	public void listLocationArray()
	{
		for(int i = 0; i < TextureDataArray.length; i++)
		{
			if(TextureDataArray[i] != null)
			{
			System.out.println(TextureDataArray[i].getLocation());
			}
		}
	}
	public void listTypeArray()
	{
		for(int i = 0; i < TextureDataArray.length; i++)
		{
			if(TextureDataArray[i] != null)
			{
			System.out.println(TextureDataArray[i].getType());
			}
		}
	}
	
	///<summary>
	///Mutators
	///<summary>
	public String getTypeAt(int _index){
		if(TextureDataArray[_index] != null)
		    return TextureDataArray[_index].getType();
		else
			return "null";
	}
	public String getDescriptionAt(int _index){
		if(TextureDataArray[_index] != null)
		    return TextureDataArray[_index].getDescription();
		else
			return "null";
	}
	public String getFileLocationAt(int _index){
		if(TextureDataArray[_index] != null)
		    return TextureDataArray[_index].getLocation();
		else
			return "null";
	}
	public String getUsableFileLocationAt(int _index)
	{
		if(TextureDataArray[_index] != null)
		    return TextureDataArray[_index].getDoubleSlashLocation();
		else
			return "null";
	}
	
	public void setTypeAt(int _index, String _type){TextureDataArray[_index].setType(_type);}
	public void setDescriptionAt(int _index, String _description){TextureDataArray[_index].setDescription(_description);}
	public void setLocationAt(int _index, String _location){TextureDataArray[_index].setLocation(_location);}
	
	//Description: object name; 
	//Location:File Location;
	//Type:Flag type;
	
}

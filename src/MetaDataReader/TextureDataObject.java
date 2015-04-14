package MetaDataReader;
	///<summary>
	///Holds parsed meta data
	///<summary>

public class TextureDataObject {

	private String type, description, location, doubleSlashLocation;
	
	public TextureDataObject(String _type, String _description, String _location)
	{
		this.type = _type;
		this.description = _description;
		this.location = _location;
		
		if(location != null)
		{
			doubleSlashLocation = location.replaceAll("\\\\", "\\\\\\\\");
		}
	}

	///<summary>
	///Mutators
	///<summary>
	public String getType(){return type;}	
	public String getDescription(){return description;}	
	public String getLocation(){return location;}
	public String getDoubleSlashLocation(){ return doubleSlashLocation;}
	
	public void setType(String _type){type = _type;}	
	public void setDescription(String _description){description = _description;}	
	public void setLocation(String _location){location = _location;}
	
	//Description: object name; 
	//Location:File Location;
	//Type:Flag type;
}

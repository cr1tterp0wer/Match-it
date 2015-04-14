package Animation.Effects;



import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Text {

	private SpriteSheet image;
	private  String str;
	private int[] characterArray;
	private float  x, y;
	private float width, height;

	public Text() throws SlickException
	{
		width = 50;
		height = 50;

		try{
			init();

		}catch(SlickException e){}
	}

	public void init() throws SlickException
	{
		try{
			image =new SpriteSheet( new Image("Content/ImageFiles/fonts_trans.png"), (int)width, (int)height);

		}catch(SlickException e){}
	}

	public void draw(String _str, float _x, float _y)
	{
		x=_x-width;
		y=_y;
		str = _str;
		Image sprite = image.getSubImage(0, 0);
		characterArray = new int[str.length()];
		for(int i =0; i < str.length(); i++)
		{
			characterArray[i] = getLetterX(_str.charAt(i));
			if(characterArray[i] < 13)
			{
				sprite = image.getSubImage(characterArray[i],0);
				System.out.println("BELOW 13");
			}
				
			else if(characterArray[i] < 26)
			{
				sprite = image.getSubImage( characterArray[i]-13,1);
			}
			else if(characterArray[i] > 26)
			{
				sprite = image.getSubImage( characterArray[i]-26,2);
			}

			sprite.draw(x+=width,y);
		}

	}

	
	public void draw(String _str, float _x, float _y, float _width, float _height, Color color)
	{
		width = _width;
		height = _height;
		
		x=_x-width;
		y=_y;
		str = _str;
		Image sprite = image.getSubImage(0, 0);
		characterArray = new int[str.length()];
		for(int i =0; i < str.length(); i++)
		{
			characterArray[i] = getLetterX(_str.charAt(i));
			if(characterArray[i] < 13)
			{
				sprite = image.getSubImage(characterArray[i],0);
			}
				
			else if(characterArray[i] < 26)
			{
				sprite = image.getSubImage( characterArray[i]-13,1);
			}
			else if(characterArray[i] >= 26)
			{
				sprite = image.getSubImage( characterArray[i]-26,2);
			}

			sprite.draw(x+=width*0.7,y, width, height, color);
		}

	}
	private int getLetterX(char c)
	{
		switch(c)
		{
		case'a':
		case 'A': return 0;
		case 'b':
		case 'B': return 1;
		case 'c':
		case 'C': return 2;
		case 'd':
		case 'D': return 3;
		case 'e':
		case 'E': return 4;
		case 'f':
		case 'F': return 5;
		case 'g':
		case 'G': return 6;
		case 'h':
		case 'H': return 7;
		case 'i':
		case 'I': return 8;
		case 'j':
		case 'J': return 9;
		case 'k':
		case 'K': return 10;
		case 'l':
		case 'L': return 11;
		case 'm':
		case 'M': return 12;
		case 'n':
		case 'N': return 13;
		case 'o':
		case 'O': return 14;
		case 'p':
		case 'P': return 15;
		case 'q':
		case 'Q': return 16;
		case 'r':
		case 'R': return 17;
		case 's':
		case 'S': return 18;
		case 't':
		case 'T': return 19;
		case 'u':
		case 'U': return 20;
		case 'v':
		case 'V': return 21;
		case 'w':
		case 'W': return 22;
		case 'x':
		case 'X': return 23;
		case 'y':
		case 'Y': return 24;
		case 'z':
		case 'Z': return 25;

		case '1': return 26;
		case '2': return 27;
		case '3': return 28;
		case '4': return 29;
		case '5': return 30;
		case '6': return 31;
		case '7': return 32;
		case '8': return 33;
		case '9': return 34;
		case '0': return 35;
		case ' ': return 36;
		case '<': return 37;
		case '>': return 38;
		case '-': return 36;
		default : return 36;


		}
	}


}

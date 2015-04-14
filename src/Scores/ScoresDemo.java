package Scores;

import java.io.*;

public class ScoresDemo {
	
	public static int counter = 0;
	
	public static ScoresInfo retrieveScores() {
		ScoresInfo list = null;
		
	    try {
	      File f = new File("scores.ser");
	      if (f.exists()) {
	    	  FileInputStream fichier = new FileInputStream(f);
		      ObjectInputStream ois = new ObjectInputStream(fichier);
		      list = (ScoresInfo) ois.readObject();
	      } else {
	    	  list = new ScoresInfo();
	      }
	      
} 
	    catch (java.io.IOException e) {
	      e.printStackTrace();
	    }
	    catch (ClassNotFoundException e) {
	      e.printStackTrace();
	    }
	    
	    return list;
	}
	
	public static void saveScores(ScoresInfo list) {
	    try {
	        FileOutputStream fichier = new FileOutputStream("scores.ser");
	        ObjectOutputStream oos = new ObjectOutputStream(fichier);
	        oos.writeObject(list);
	        oos.flush();
	        oos.close();
	      }
	      catch (java.io.IOException e) {
	        e.printStackTrace();
	      }

	}

	public static void main(String[] args) {
		
		// test ScoresInfo constructor
		//ScoresInfo list = new ScoresInfo();
		ScoresInfo list = retrieveScores();
		
		// test Score constructor
		displayTest("Score constructors and toString method");
		Score score1 = new Score();
		Score score2 = new Score("sylvain");
		System.out.println(score1);
		System.out.println(score2);
		
		// test setters of Score
		displayTest("Score setters");
		score1.setPlayerName("toto");
		score2.setScore(100);
		System.out.println(score1);
		System.out.println(score2);
		
		// test getters of Score
		displayTest("Score getters");
		System.out.print("score1: ");
		System.out.print(score1.getPlayerName() + " -- ");
		System.out.print(score1.getScore() + " -- ");
		System.out.println(score1.getDate());
		System.out.print("score2: ");
		System.out.print(score2.getPlayerName() + " -- ");
		System.out.print(score2.getScore() + " -- ");
		System.out.println(score2.getDate());
		
		// test add and subtract to Score
		displayTest("Score add and subtract");
		score1.addToCurrent(356);
		score2.subFromCurrent(75);
		System.out.println(score1);
		System.out.println(score2);

		// test reset Score
		displayTest("Score reset");
		score1.reset();
		score2.reset();
		System.out.println(score1);
		System.out.println(score2);
		
		// test Score comparison
		displayTest("Score: comparisons");
		score1.setScore(150);
		score2.setScore(200);
		System.out.println("-----compareTo");
		System.out.println(score1);
		System.out.println(score2);
		System.out.println (score1.compareTo(score2) < 0 ?
				            "score1 < score2" :
				            "score1 >= score2");
		score1.setScore(750);
		System.out.println(score1);
		System.out.println(score2);
		System.out.println (score1.compareTo(score2) < 0 ?
				            "score1 < score2" :
				            "score1 >= score2");
		System.out.println("-----equals");
		score2.setScore(750);
		System.out.println(score1);
		System.out.println(score2);
		System.out.println (score1.equals(score2) ?
				            "score1 = score2" :
				            "score1 != score2");

		score2.setScore(751);
		System.out.println(score1);
		System.out.println(score2);
		System.out.println (score1.equals(score2) ?
				            "score1 = score2" :
				            "score1 != score2");
		
		// test ScoresInfo addEntry(), toString
		displayTest("ScoresInfo addEntry and toString methods");
		System.out.print("adding entry... ");
		System.out.println(list.addEntry(score1) == 0 ?
				            "new record!" :
				            "no record...");
		System.out.print("adding entry... ");
		System.out.println(list.addEntry(score2) == 0 ?
	            "new record!" :
	            "no record...");
		System.out.println(list);
		
		System.out.println("Add new scores (in this order): " +
				           "750, 300, 1050, 600, 856, 1050");
		Score score3 = new Score("player3"); score3.setScore(750);
		Score score4 = new Score("player4"); score4.setScore(300);
		Score score5 = new Score("player5"); score5.setScore(1050);
		Score score6 = new Score("player6"); score6.setScore(600);
		Score score7 = new Score("player7"); score7.setScore(856);
		Score score8 = new Score("player8"); score8.setScore(1050);
		System.out.print("adding entry... ");
		System.out.println(list.addEntry(score3) == 0 ?
	            "new record!" :
	            "no record...");
		System.out.print("adding entry... ");
		System.out.println(list.addEntry(score4) == 0 ?
	            "new record!" :
	            "no record...");
		System.out.print("adding entry... ");
		System.out.println(list.addEntry(score5) == 0 ?
	            "new record!" :
	            "no record...");
		System.out.print("adding entry... ");
		System.out.println(list.addEntry(score6) == 0 ?
	            "new record!" :
	            "no record...");
		System.out.print("adding entry... ");
		System.out.println(list.addEntry(score7) == 0 ?
	            "new record!" :
	            "no record...");
		System.out.print("adding entry... ");
		System.out.println(list.addEntry(score8) == 0 ?
	            "new record!" :
	            "no record...");
		System.out.println(list);

		// test ScoresInfo addEntry(), toString
		displayTest("ScoresInfo: best score and best player");
		System.out.println("Best score (as object): " 
		                   + list.getBestScoreObject());
		System.out.println("Best score (as integer): " 
                + list.getBestScore());
		System.out.println("Best player: " 
                + list.getBestPlayer());

		
		saveScores(list);
		
	}
	
	//----------------------------------------------------------------------
	//  Helper method
	//----------------------------------------------------------------------	
	/**
	 * Display a header for each test
	 * @param test the string to display
	 */
	public static void displayTest(String test) {
		counter++;
		System.out.println("\n==============================================");
		System.out.printf("Test #%2d: %s\n\n", counter, test);
	}
}
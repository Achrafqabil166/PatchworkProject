package patchwork;

import java.util.Scanner;

public class ChoiceLevel {
	
	private Scanner scanner;
	
	public ChoiceLevel() {
		this.scanner = new Scanner(System.in);
	}

	@Override
	public String toString() {
		return "choose your level of play: (1) Phase1 / (2) Phase2 / (0) Exit";
	}
	
	public int choose(){
		int choose = scanner.nextInt();
		
		switch (choose) {
		case 1: 
			return 1;
		case 2: 
			return 2;
		case 0: 
			return 0;
		default:
			throw new IllegalArgumentException("Unexpected value: " + choose);
		}		
	}
}

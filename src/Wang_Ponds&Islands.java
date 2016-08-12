/******************************************************************************
 * 
 * Name:		Justin Wang
 * Block:		G
 * Date:		11/22/13
 * 
 *  Program #2:	Ponds and Islands
 *  Description:
 *     In this program the program receives a text file. The program deciphers this text file 
 *     that is a grid of ponds and islands. The program will flood fill any connected land, 
 *     that is land connected sideways or diagonals, with the same letter. If the land is not
 *     connected, there is a different letter used. This process is repeated for water, in
 *     which numbers are used, and diagonals do not count as being connected.
 * 
 ******************************************************************************/
import java.awt.*;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;

public class PondsAndIslands 
{
	public static final char WATER = '.';
	public static final char LAND = 'X';	

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{

		Scanner s = openFile();
		Scanner console = new Scanner(System.in);
		char [][] grid = getInput(s); // reads in text file
		print(grid); // prints initial text file
		// flood fills land
		char label = 'a';
		for(int row = 0; row < grid.length; row++)
		{
			for(int col = 0; col < grid[0].length; col ++)
			{
				if (grid[row][col] == 'X')
				{
					floodFillLand(grid, row, col, label);
					label ++;
				}
			}
		}
		// flood fills water
		label = '1';
		for(int row = 0; row < grid.length; row++)
		{
			for(int col = 0; col < grid[0].length; col ++)
			{
				if (grid[row][col] == '.')
				{
					floodFillWater(grid, row, col, label);
					label ++;
				}
			}
		}
		print(grid); // prints final grid
		// TODO Auto-generated method stub

	}
/**
 * 
 * @return opens the text file
 */
	public static Scanner openFile()
	{

		Scanner s = null;
		try 
		{

			s = new Scanner((new FileReader("map.txt")));

		}
		catch (IOException e)
		{	
			// Something went wrong!
			System.out.println("File error - file not found, could not be " +
					"opened or could not be read.");
		}
		return s;

	}
/**
 * 
 * @param s
 * @return returns a double array of characters of ponds and islands
 */
	public static char[][] getInput (Scanner s)
	{
		char [][] fakeGrid = new char [1][1]; // fake grid that is not actually returned
		int numRows = 0;
		int numCols = 0;
		while (s.hasNext()) 
		{
			int input = s.nextInt();
			numRows = input;
			input = s.nextInt();
			numCols = input;
			s.nextLine();
			char grid[][] = new char [numRows][numCols];
			for(int row = 0; row < grid.length; row++)
			{
				String Input = s.nextLine();

				for(int col = 0; col < grid[0].length; col++)
				{
					if (Input.charAt(col) == 'X' || Input.charAt(col) == '.')
					{
					grid[row][col] = Input.charAt(col);
					}
					else
					{
						System.out.print("INVALID TEXT FILE"); // checks for invalid text file
					}
				}
			}
			return grid;
		}
		return fakeGrid;
	}
/**
 * prints out the grid
 * @param grid
 */
	public static void print(char [][] grid)
	{
		for(int row = 0; row < grid.length; row++)
		{
			for(int col = 0; col < grid[0].length; col ++)
			{
				System.out.print(grid[row][col]);
			}
			System.out.println();
		}
		System.out.println();
	}
/**
 * recursively flood fills land
 * base cases of the location not being land, already having a label, or being out of bounds
 * Checks all 8 surrounding boxes for land
 * @param grid
 * @param row
 * @param col
 * @param label
 */
	public static void floodFillLand(char [][] grid, int row, int col, char label)
	{
		if(row < 0 || row >= grid.length || col < 0 || col >= grid[0].length)
		{
			return;
		}
		else if (grid[row][col] != LAND)
		{
			return;
		}
		else{
			grid[row][col] = label;
			//right
			if (col + 1 < grid[0].length && grid[row][col + 1] == LAND)
			{
				floodFillLand(grid, row, col + 1, label);
			}
			//bottom right
			if (row + 1 < grid.length && col + 1 < grid[0].length && grid[row + 1][col + 1] == LAND)
			{
				floodFillLand(grid, row + 1, col + 1, label);
			}
			//bottom
			if (row + 1 < grid.length && grid[row + 1][col] == LAND)
			{
				floodFillLand(grid, row + 1, col, label);
			}
			//bottom left
			if (row + 1 < grid.length && col - 1 >= 0 && grid[row + 1][col - 1] == LAND)
			{
				floodFillLand(grid, row + 1, col - 1, label);
			}
			//left
			if (col - 1 >= 0 && grid[row][col - 1] == LAND)
			{
				floodFillLand(grid, row, col - 1, label);
			}
			//top right
			if (row - 1 >= 0 && col -1 >= 0 && grid[row - 1][col - 1] == LAND)
			{
				floodFillLand(grid, row - 1, col - 1, label);
			}
			//top
			if (row - 1 >= 0 && grid[row - 1][col] == LAND)
			{
				floodFillLand(grid, row - 1, col, label);
			}
			//top left
			if (row - 1 >= 0 && col + 1 < grid[0].length && grid[row - 1][col + 1] == LAND)
			{
				floodFillLand(grid, row - 1, col + 1, label);
			}
		}
	}
/**
 * Recursively flood fills water
 * Checks four boxes above, below, and to the right and left
 * base cases of box not being water, already having a label, or being out of bounds
 * @param grid
 * @param row
 * @param col
 * @param label
 */
	public static void floodFillWater(char [][] grid, int row, int col, char label)
	{
		if(row < 0 || row >= grid.length || col < 0 || col >= grid[0].length)
		{
			return;
		}
		else if (grid[row][col] != WATER)
		{
			return;
		}
		else{
			grid[row][col] = label;
			//right
			if (col + 1 < grid[0].length && grid[row][col + 1] == WATER)
			{
				floodFillWater(grid, row, col + 1, label);
			}
			//left
			if (col - 1 >= 0 && grid[row][col - 1] == WATER)
			{
				floodFillWater(grid, row, col - 1, label);
			}
			//bottom
			if (row + 1 < grid.length && grid[row + 1][col] == WATER)
			{
				floodFillWater(grid, row + 1, col, label);
			}
			//top
			if (row - 1 >= 0 && grid[row - 1][col] == WATER)
			{
				floodFillWater(grid, row - 1, col, label);
			}
		}
	}
}

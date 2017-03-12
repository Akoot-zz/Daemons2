package com.Akoot.daemons.test;

public class Test
{
	public static void main(String[] args)
	{
		long now = System.currentTimeMillis();
		start();
		System.out.println("Finished in " + (float) ((System.currentTimeMillis() - now) / 1000.000F) + " seconds.");
	}
	
	public static void start()
	{
		System.out.println("Gangrene Reflexes 2.0");
	}
}

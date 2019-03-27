/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.kniedzwiecki.ztpj.lab02;

import eu.kniedzwiecki.ztpj.lab02.entities.EPosition;
import java.sql.SQLException;

/**
 *
 * @author knied
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
		
		try
		{
			EPosition p = EPosition.getPositionFromId(0);
			System.out.println(p.toString());
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
}

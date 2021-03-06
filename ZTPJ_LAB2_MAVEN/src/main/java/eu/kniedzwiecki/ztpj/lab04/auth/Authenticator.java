package eu.kniedzwiecki.ztpj.lab04.auth;
import java.rmi.*;
import java.rmi.server.*;
import java.time.Instant;
import java.util.*;

public class Authenticator extends UnicastRemoteObject implements IAuthenticator 
{
	public class Token
	{
		public String User;
		public Instant ExpDate;
		private boolean Used = false;
		boolean IsValid() { return ExpDate.isAfter(Instant.now()) && !Used; }
		void Use() { Used = true; }
	}
	
	private final Map<String, String> Users = new HashMap<>();
	private final Map<String, Token> Tokens = new HashMap<>();
	private final int TokenLifeLengthSeconds = 2*60;
	
	public Authenticator() throws RemoteException
	{
		super();
		Users.put("krzysztof", "pwd");
	}
	
	public Token getTokenData(String token)
	{ 
		//UUID token_uuid = UUID.fromString(token);
		Token t = Tokens.get(token);
		return t;
	}
	
	@Override
	public String authenticate(String username, String password) throws RemoteException
	{
		String pwd = Users.get(username);
		if(pwd == null) return null;
		if(!pwd.equals(password)) return null;
		
		Token t = new Token();
		t.User = username;
		t.ExpDate = Instant.now();
		t.ExpDate = t.ExpDate.plusSeconds(TokenLifeLengthSeconds);
		UUID tokenUuid = UUID.randomUUID();
		Tokens.put(tokenUuid.toString(), t);
		
		return tokenUuid.toString();
	}
	
}

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : IAccountDatabase.java
//  @ Date : 2016-11-21
//  @ Author : 
//
//
package project.android.com.android5777_9254_6826.model.datasource;


import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import project.android.com.android5777_9254_6826.model.entities.Account;

public interface IAccountDatabase extends IDatabase {
	/**
	 *
	 * @param UserName
	 * @param Password
     * @return true - if succeeded , else false;
     */
	int addNewAccount(String UserName, String Password);
	int addNewAccount(Account toInsert);
	ArrayList<Account> getAccountList();
	Cursor CgetAccountList();
	Account getAccount(long id) throws Exception;
	Account getAccount(String username) throws Exception;
	boolean isRegistered(String userName);

	/**
	 * checks if the account exists and returns true if the passToCheck equals to the Account's password
	 * if the account doesnt exists throws exception.
	 * @param userName hello
	 * @param passToCheck
	 * @return true if the passtoCheck equals to the account's password
	 * @throws Exception if the account was not found.
     */
	boolean verifyPassword(String userName,String passToCheck) throws Exception;
	int removeAccount(String username);
	int removeAccount(int rowID);
	Uri insert(Account ac);
	int delete(Uri uri, String selection, String[] selectionArgs);
	int update(Uri uri, ContentValues values, String selection,
					  String[] selectionArgs);
	Cursor query(Uri uri, String[] projection, String selection,
						String[] selectionArgs, String sortOrder);
}

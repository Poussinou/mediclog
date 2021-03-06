/* 
*   Copyright (C) 2018  John Lines <john.mediclog@paladyn.org>
*
*    This program is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    This program is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with this program.  If not, see <http://www.gnu.org/licenses/>
*/

package org.paladyn.mediclog;

import java.io.File;
import java.io.FileNotFoundException;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;


public class LocalFileProvider extends ContentProvider {

	private static final String CLASS_NAME = "LocalFileProvider";

	public static final String AUTHORITY = "org.paladyn.mediclog.LocalFileProvider";

	private UriMatcher uriMatcher;


	@Override
	    public boolean onCreate() {
		            uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	            // Add a URI to the matcher which will match against the form
		    // 'content://it.my.app.LogFileProvider/*'
		    // and return 1 in the case that the incoming Uri matches this pattern
		                 uriMatcher.addURI(AUTHORITY, "*", 1);
		    
                                 return true;
                    }

@Override
    public ParcelFileDescriptor openFile(Uri uri, String mode)
                throws FileNotFoundException {

		        String LOG_TAG = CLASS_NAME + " - openFile";

			        Log.v(LOG_TAG,
						                "Called with uri: '" + uri + "'." + uri.getLastPathSegment());

				        // Check incoming Uri against the matcher
					        switch (uriMatcher.match(uri)) {
				
			                // If it returns 1 - then it matches the Uri defined in onCreate
			         case 1:
					//
// The desired file name is specified by the last segment of the
// path
// E.g.
// 'content://it.my.app.LogFileProvider/Test.txt'
// Take this and build the path to the file


			String fileLocation = getContext().getFilesDir() + File.separator
			                     + uri.getLastPathSegment();

                        // Create & return a ParcelFileDescriptor pointing to the file
                       // Note: I don't care what mode they ask for - they're only getting
                       // read only
              ParcelFileDescriptor pfd = ParcelFileDescriptor.open(new File(
	          fileLocation), ParcelFileDescriptor.MODE_READ_ONLY);
		return pfd;
		
	// Otherwise unrecognised Uri
	default:
	 Log.v(LOG_TAG, "Unsupported uri: '" + uri + "'.");
         throw new FileNotFoundException("Unsupported uri: "
	    + uri.toString());
	}
	}


@Override
    public int update(Uri uri, ContentValues contentvalues, String s,
		                          String[] as) {
	            return 0;
    }

   @Override
       public int delete(Uri uri, String s, String[] as) {
	               return 0;
		           }

       @Override
           public Uri insert(Uri uri, ContentValues contentvalues) {
		           return null;
			       }

           @Override
	       public String getType(Uri uri) {
		               return null;
			           }

	       @Override
	           public Cursor query(Uri uri, String[] projection, String s, String[] as1,
				                           String s1) {
			           return null;
		   }


}

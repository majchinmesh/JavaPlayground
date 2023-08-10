package org.example;

import org.apache.hadoop.conf.Configuration;
import org.example.oozie.dbencdec.CodecFactory;
import org.example.oozie.dbencdec.GzipCompressionCodec;
import org.example.oozie.dbencdec.StringBlob;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.StringReader;
import java.util.Base64;
import java.util.Objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Base64;

public class SQLDatabaseConnection {


    public static void main(String[] args) {
        String connectionUrl =
                "jdbc:sqlserver://"+args[0]+";"
                        + "databaseName="+args[1]+";"
                        + "user="+args[2]+";"
                        + "password="+args[3]+";"
                        + "encrypt=true;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;"
                        + "hostNameInCertificate=*.database.windows.net;";
        System.out.println("Hello there!");
        System.out.println(connectionUrl);
        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {

            // Create and execute a SELECT SQL statement.
            String selectSql = " select top 10 id, conf from WF_JOBS ";
            resultSet = statement.executeQuery(selectSql);

            // Print results from select statement
            while (resultSet.next()) {
                String hexString = resultSet.getString(2);

                byte[] byteArray = hexStringToByteArray(hexString);
                String base64EncodedString = Base64.getEncoder().encodeToString(byteArray);

//                System.out.println("Base64 Encoded String: " + base64EncodedString);

	       // Convert the base64 to xml

		String decodedOutput = DecodeBase64(base64EncodedString);
		System.out.println("Decoded Base64  String: " + decodedOutput);
//		
            }
        }

        catch (Exception e) {
                    e.printStackTrace();
        }
   }
    public static byte[] hexStringToByteArray(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }


    public static String DecodeBase64(String base64String) throws Exception{
	try {
	     Configuration conf = new Configuration();
             conf.set(CodecFactory.COMPRESSION_CODECS, "gz=" + GzipCompressionCodec.class.getName());
             conf.set(CodecFactory.COMPRESSION_OUTPUT_CODEC, GzipCompressionCodec.CODEC_NAME);
             CodecFactory.initialize(conf);

             String ucc = new StringBlob(Base64.getDecoder().decode(base64String)).getString();
	    return ucc;
	}catch (Exception e) {
            throw new Exception("Error decoding Base64 string: " + e.getMessage(), e);
        }
    }


}   

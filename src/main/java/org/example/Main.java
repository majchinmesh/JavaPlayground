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

public class Main {
    public static void main(String[] args) throws Exception {
        if(args.length < 2){
            System.out.println("Need 2 args, i.e. 0 <compressed string> or 1 <uncompressed string>");
            return ;
        }
        Configuration conf = new Configuration();
        conf.set(CodecFactory.COMPRESSION_CODECS, "gz=" + GzipCompressionCodec.class.getName());
        conf.set(CodecFactory.COMPRESSION_OUTPUT_CODEC, GzipCompressionCodec.CODEC_NAME);
        CodecFactory.initialize(conf);

        if(args[0].equals("0")) {
            String cc = "T0JKAAAAAQAAAAEABWNvZGVjAAJneh+LCAAAAAAAAADVlMtOwzAQRfdI/AM/4EcC5VFFFUhs6QLBhp1jT1sXx2PGNin9etIUkJDSRZC6YBN5Yvn45s7NVBr9wi4zqWTRz05Pzs6qQBiA0kdfdbVXDcwwp5DTvaVK9PXX3rtyGWaNCozAZA2V2L/pQeIXaZgLG9UEB/ERMQ2hv/dHg98yZJh3qyGqgYXKLo2GtkivC4ftXQjPZIfIrYp1nAqBW1M3djkp2pqVsjxn8oqVl6ko2cUlK29YcXW+vVU5YdTKAemE6HZnee2w5hoJeGu9wTZyD0nkCCQQtxbEtyNChRCFJvQs6lXnvhvvfkyKBm3/kTx5knIq5cto9E4x9wf8779kfFa8Oaz1utda/EkrQcRMGh6UV0sYjPjKS7bvKW+otfIjrXxZgPfKru2bXSbCteF6w61PQF45rh1m0/Vo17/ptZzI0bJ6l7osIBnegZzV/V/Kg0qr/5+9bmjsZwZfd7ceIS67xxzNIPE4Vh2UWInfc/YTh2fieHcFAAA=";
            cc = args[1];
            String ucc = new StringBlob(Base64.getDecoder().decode(cc)).getString();
            System.out.println(ucc);
        }else if (args[0].equals("1")) {
            String ucc = "abc";
            ucc = args[1];
            StringBlob sb = new StringBlob(ucc);
            String ccn = Base64.getEncoder().encodeToString(sb.getRawBlob());
            System.out.println(ccn);
        }else {
            System.out.println("Invalid first arg, must be either 0 to decompress a string, or 1 to compress the string");
        }
    }

    public static Element parseXml(String xmlStr) throws JDOMException {
        Objects.requireNonNull(xmlStr, "xmlStr cannot be null");
        try {
            SAXBuilder saxBuilder = createSAXBuilder();
            Document document = saxBuilder.build(new StringReader(xmlStr));
            return document.getRootElement();
        }
        catch (Exception ex) {
            throw new RuntimeException("Xml parsing failed, " + ex.getMessage(), ex);
        }
    }
    private static SAXBuilder createSAXBuilder() {
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setFeature("http://apache.org/xml/features/disallow-doctype-decl",true);
        saxBuilder.setFeature("http://xml.org/sax/features/external-general-entities", false);
        saxBuilder.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        saxBuilder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        return saxBuilder;
    }
}
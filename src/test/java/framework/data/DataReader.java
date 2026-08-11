//package framework.data;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.commons.io.FileUtils;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.List;
//
//public class DataReader {
//
//    public List<HashMap<String, String>> getJasonDataToMap() throws IOException {
//
//        //read json to String
//        String jsonFilePath = System.getProperty("user.dir") + "/src/test/java/framework/data/PurchaseOrder.json";
//        String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);
//
//        //String to HashMap
//        ObjectMapper mapper = new ObjectMapper();
//        List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>(){});
//        return data;
//
//    }
//}

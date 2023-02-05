package aa;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Sample {

	public static void main(String[] args) {
		Map<String, Integer> headCountMap = new HashMap<>();
		headCountMap.put("Facebook", 2000);
		headCountMap.put("Microsoft", 20000);
		headCountMap.put("Google", 15000);
		headCountMap.put("Apple", 5000);
		headCountMap.put("Twitter", 3000);
		headCountMap.put("Linkedin", 6000);

		Map<String, Integer> sortedMap = headCountMap.entrySet().parallelStream()
						.sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
						.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));//Line 11
		sortedMap.forEach((company,headCount)->System.out.println("Company : "+company+" headCount : "+headCount)); //Line 12

	}
}
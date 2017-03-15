package com.wechat.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wechat.entity.Answer;
import com.wechat.entity.Knowledge;
import com.wechat.entity.TestKnowledge;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 算法服务
 * 
 * @author pc
 *
 */
@Service
public class ArithmeticService {

	
	/**
	 * 掌握率
	 * @param answers,每道题的信息集合，包含每道题的平均分数，所有知识点集合，是否正确，每道题每个知识点对应的分数（TestKnowledge中的属性knowlegesScore）
	 * @return
	 */
	public  String getGraspRate(List<Answer> answers){
		
		List<TestKnowledge> tests = new ArrayList<>();//集合中的每个对象的解释：题目分值，所有的知识点，知识点对应的分数，是否正确 
		//Answer中已经将属性赋值给TestKnowledge
		for (Answer an : answers) {
			tests.add(an.getTestKnowledge());
		}
		Map<String, Integer> map = getRight(tests);
		JSONObject json = JSONObject.fromObject(map); 
		return json.toString();
	}
	
	/**
	 * 获取知识点错误频率
	 * @param answers
	 * @return
	 */
	public  String getErrorFrequency(List<Answer> answers){
		List<TestKnowledge> list = new ArrayList<>();
		for (Answer an : answers) {
			list.add(an.getTestKnowledge());
		}
		Map<String, Knowledge> map = errorFrequency(list);
		if(map.size()==0){
			return "";
		}
		JSONArray ar = new JSONArray();
		for(Entry<String, Knowledge> en : map.entrySet()){
			Knowledge kn = en.getValue();
			JSONObject obj = new JSONObject();
			obj.put("knowledgeName",kn.getKnowledgeName());
			obj.put("knowledgeCode",kn.getKnowledgeCode());
			obj.put("errorSize",kn.getErrorSize());
			ar.add(obj);
		}
		 
		return ar.toString();
	}

	/**
	 * 获取错误知识点
	 *
	 * @param testContent
	 * @return
     */
	public String getErrorFrequency(String testContent){
		if (StringUtils.isBlank(testContent)) {
			return "";
		}
		try {
			JSONObject json = JSONObject.fromObject(testContent);
			JSONArray questions = json.getJSONArray("questions");
			List<JSONObject> productionModelList = new ArrayList<>();
			for (int i = 0; i < questions.size(); i++) {
				JSONObject question = questions.getJSONObject(i);
				JSONArray productionModels = question.getJSONArray("productionModelstwo");
				for (int j = 0; j < productionModels.size(); j++) {
					productionModelList.add(productionModels.getJSONObject(j));
				}
			}
			if (productionModelList.isEmpty())
				return "";
			Map<String, Knowledge> knowledgeMap = new HashMap<>();
			for (JSONObject pro : productionModelList) {
				int isTrue = pro.getInt("isTrue");
				String knowledgeName = pro.getString("name");
				String knowledgeCode = pro.getString("id");
				if (isTrue == 0) {
					Knowledge knowledge = knowledgeMap.get(knowledgeCode);
					Integer n = knowledge==null ? 0: knowledge.getErrorSize();
					if(knowledge==null){
						knowledge = new Knowledge();
						knowledge.setKnowledgeName(knowledgeName);
						knowledge.setKnowledgeCode(knowledgeCode);
					}
					knowledge.setErrorSize(n+1);
					knowledgeMap.put(knowledgeCode,knowledge);
				}
			}
			if(knowledgeMap.size()==0){
				return "";
			}
			JSONArray errorArray = new JSONArray();
			for(Entry<String, Knowledge> en : knowledgeMap.entrySet()){
				errorArray.add(JSONObject.fromObject(en.getValue()));
			}
			return errorArray.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	/**
	 * 知识点掌握程度
	 * 
	 * @param tests
	 * @return
	 */
	private static Map<String, Integer> getRight(List<TestKnowledge> tests) {
		Map<String, Double> rightMap = new HashMap<>();//这套试卷中每个知识点正确的总得分（去除错误的分数）
		Map<String, Double> allMap = new HashMap<>();//这套试卷中每个知识点的总得分（包含错误与正确的分数）
		//利用map键的唯一性和if判断进行累加和去重
		for (TestKnowledge test : tests) {
			for (Knowledge kn : test.getKnowledges()) {
				Double d = rightMap.get(kn.getKnowledgeName()) == null ? 0.0 : rightMap.get(kn.getKnowledgeName());
				if (test.getIsRight() == true) {
					rightMap.put(kn.getKnowledgeName(), d + test.getKnowlegesScore());
				}else{
					rightMap.put(kn.getKnowledgeName(), d);
				}
				Double all = allMap.get(kn.getKnowledgeName())==null ? 0.0:allMap.get(kn.getKnowledgeName());
				allMap.put(kn.getKnowledgeName(), all + test.getKnowlegesScore());
			}
		}
		Map<String, Integer> newMap = new HashMap<>();//每个知识点的正确率，键值对
		for (Entry<String, Double> en : rightMap.entrySet()) {
			Double d = en.getValue() / allMap.get(en.getKey())*100;
			newMap.put(en.getKey(),d.intValue());
		}

		return newMap;
	}
	
	/**
	 * 获取错误频率
	 * 
	 * @param tests
	 * @return
	 */
	private static Map<String, Knowledge> errorFrequency(List<TestKnowledge> tests) {
		Map<String, Knowledge> map = new HashMap<>();
		for (TestKnowledge test : tests) {
			for (Knowledge kn : test.getKnowledges()) {
				if (test.getIsRight() == false) {
					 Knowledge k = map.get(kn.getKnowledgeName());
					 Integer n = k==null ? 0: k.getErrorSize();
					 if(k==null){
						 k = new Knowledge();
						 k.setKnowledgeName(kn.getKnowledgeName());
						 k.setKnowledgeCode(kn.getKnowledgeCode());
					 }
					 k.setErrorSize(n+1);
					 map.put(kn.getKnowledgeName(),k);
				}
			}
		}
		return map;
	}

	public static void main(String[] args) {
		String testContent = "{\"paperId\":\"8a2a74685920e2f001592fcbdff01e46\",\"subjectCode\":\"3\",\"questions\":[{\"questionId\":\"8a2a7468593fc45e0159488ff1fb4e4f\",\"productionModels\":[{\"basenameVo\":\"名词类选项分析\",\"basecodeVo\":\"03710001000100040001\",\"typecodeVo\":\"lijielianjiexing\",\"sons\":[{\"idVo\":\"20040004000100040001\",\"nameVo\":\"名词类选项分析\"}],\"isTrue\":0},{\"basenameVo\":\"语境暗示\",\"basecodeVo\":\"0371000100050005\",\"typecodeVo\":\"fangfafenxilei\",\"sons\":[{\"idVo\":\"2004000400050005\",\"nameVo\":\"语境暗示\"}],\"isTrue\":0}],\"score\":2},{\"questionId\":\"8a2a7468593fc45e0159488ee27e4e1c\",\"productionModels\":[{\"basenameVo\":\"根据上下文情景猜词（语））义\",\"basecodeVo\":\"0370000100040005\",\"typecodeVo\":\"fangfafenxilei\",\"sons\":[{\"idVo\":\"2004000100040005\",\"nameVo\":\"根据上下文情景猜词（语））义\"}],\"isTrue\":1}],\"score\":2}],\"gradeCode\":\"33\",\"bookType\":\"rMYcDBZARPMKnzscKXNC5bXHnkRBMYst\"}";
		ArithmeticService arithmeticService = new ArithmeticService();
		System.out.println(arithmeticService.getErrorFrequency(testContent));
	}
	/**
	 * 获取错误知识点
	 *
	 * @param testContent
	 * @return
     */
	/*public String getErrorFrequency(String testContent){
		if (StringUtils.isBlank(testContent)) {
			return "";
		}
		Gson gson = new Gson();
		Map<String,Object> map1 = gson.fromJson(testContent, new TypeToken<Map<String,Object>> () {}.getType());
		Object object = map1.get("questions");
		String json = gson.toJson(object);
		List<Map<String,Object>> list1 = gson.fromJson(json, new TypeToken<List<Map<String,Object>>> () {}.getType());
		Map<String, Knowledge> errorMap = new HashMap<String, Knowledge>();
		List<Knowledge> listLastList = new ArrayList<Knowledge>();
		for (Map<String, Object> map : list1) {
			Object object2 = map.get("productionModelstwo");
			String json2 = gson.toJson(object2);
			List<Map<String,Object>> list2 = gson.fromJson(json2, new TypeToken<List<Map<String,Object>>> () {}.getType());
			for (Map<String, Object> map3 : list2) {
				String knowledgeName = map3.get("name").toString();
				String knowledgeCode = map3.get("id").toString();
				Integer n = errorMap.get(knowledgeCode)==null?0:errorMap.get(knowledgeCode).getErrorSize();
				String isTrue = map3.get("isTrue").toString();
				Knowledge knowledge = new Knowledge();
				knowledge.setKnowledgeCode(knowledgeCode);
				knowledge.setKnowledgeName(knowledgeName);
				if (isTrue.equals("0.0")) {
					n = n+1;
					knowledge.setErrorSize(n);
					errorMap.put(knowledgeCode, knowledge);
				}
			}
		}
		System.out.println("size=" + errorMap.size());
		for (String key : errorMap.keySet()) {
			listLastList.add(errorMap.get(key));
		}
		System.out.println(gson.toJson(listLastList));
		return gson.toJson(listLastList);
	}*/
}

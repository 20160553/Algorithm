import java.util.*;

class Solution {
    public int solution(String skill, String[] skill_trees) {
        /*
        skill 쪼개서 
        if now == nowSkill
        */
        int answer = 0;
        int learnedSkill = 0;
        HashMap<Character, Integer> requiredSkill = new HashMap();
        
        for(int i = 0; i < skill.length(); i++) {
            requiredSkill.put(skill.charAt(i), requiredSkill.size()+1);
        }
        
        roop1: for (String usersSkillTree: skill_trees) {
            learnedSkill = 0;
            for (int i = 0; i < usersSkillTree.length(); i++) {
                char now = usersSkillTree.charAt(i);
                //선행 스킬 없는 경우
                if (!requiredSkill.containsKey(now))
                    continue;
                //선행 스킬인 경우
                //스킬 조건 충족된 경우
                if (learnedSkill == requiredSkill.get(now) - 1) {
                    learnedSkill++;
                } else {
                    continue roop1;
                }
            }
            answer++;
        }
        return answer;
    }
}
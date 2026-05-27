import java.util.*;

class Word {
    String content;
    int start;
    int end;
    boolean isChecked;
    
    public Word(String content, int start, int end) {
        this.content = content;
        this.start = start;
        this.end = end;
        this.isChecked = false;
    }
    
    public boolean isPrev(int start) {
        return this.end < start;
    }
    
    public boolean isAfter(int end) {
        return this.start > end;
    }
    
    public boolean isSpolierWord(int s_s, int s_e) {
        if (s_s <= end || (start <= s_s && s_e <= end)) return true;
        return false;
    }
    
    public boolean equals(Word word) {
        if (this.content.equals(word.content)) return true;
        return false;
    }
    
    
    public String toString() {
        return content + " " + start + "-" + end;
    }
}

class Solution {
    public int solution(String message, int[][] spoiler_ranges) {
        /*
        1. 일부 문자만 스포 방지더라도 전체가 스포방지
        
        1. end < spoiler_start 인 경우는 단어 idx++, 
        2. 스포일러 단어에 걸치는 경우, 스포일러 인덱스, 단어 인덱스 둘다 ++, 중요 체크
        3. 반복
        
        */
        
        // 초기화
        List<Word> words = new ArrayList<>();
        HashSet<String> importantWords = new HashSet<>();
        HashSet<String> unimportantWords = new HashSet<>();
        
        int s = -1;
        String str = "";
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (c == ' ') {
                if (s == -1) continue;
                words.add(new Word(str, s, i - 1));
                s = -1;
                str = "";
                continue;
            }
            if (s == -1) s = i;
            str += c;
        }
        if (s != -1) words.add(new Word(str, s, message.length() - 1));
        
        System.out.println("words: " + words);
        
        // 중요, 안중요 구분
        int wordsIdx = 0;
        for (int spoilerIdx = 0; spoilerIdx < spoiler_ranges.length; spoilerIdx++) {
            int[] spoiler = spoiler_ranges[spoilerIdx];
            int s_s = spoiler[0];
            int s_e = spoiler[1];
            while (wordsIdx < words.size()) {
                Word w = words.get(wordsIdx);
                System.out.println("wordsContent~!" + w.content);
                if (w.isPrev(s_s)) {
                    wordsIdx++;
                    importantWords.remove(w.content);
                    unimportantWords.add(w.content);
                    w.isChecked = true;
                    continue;
                }
                if (w.isAfter(s_e)) {
                    if (spoilerIdx == spoiler_ranges.length) {
                        importantWords.remove(w.content);
                        unimportantWords.add(w.content);
                    }
                    break;
                }
                boolean isSpoiler = w.isSpolierWord(s_s, s_e);
                wordsIdx++;
                w.isChecked = true;
                if (isSpoiler) {
                    if (!unimportantWords.contains(w.content)) {
                        importantWords.add(w.content);
                    } else {
                        importantWords.remove(w.content);
                        unimportantWords.add(w.content);
                    }
                } else {
                    importantWords.remove(w.content);
                    unimportantWords.add(w.content);
                }
            }
        }
        System.out.println(wordsIdx);
        while (wordsIdx < words.size()) {
            Word w = words.get(wordsIdx);
            System.out.println("wordsContent2~!" + w.content);
            wordsIdx++;
            w.isChecked = true;

            importantWords.remove(w.content);
            unimportantWords.add(w.content);
        }
        System.out.println("unimport: " + unimportantWords);
        System.out.println("import: " + importantWords);
        
        return importantWords.size();
    }
}
import java.util.Arrays;

class Solution {
	final static int ARRAY_SIZE = 11;
	static int answerScore;
	public static int[] repetitionCombination(int n, int num, int idx, int[] arr, int[] answer, final int[] info) {

		if (num == n) {
			int myScore = 0;
			int enemyScore = 0;
			for (int i = 0; i < ARRAY_SIZE; i++) {
				if (arr[i] > info[i])
					myScore += 10 - i;
				else if (arr[i] != 0 || info[i] != 0)
					enemyScore += 10 - i;
			}
						
			if (myScore > enemyScore) {
				if (answer.length == 1 || myScore - enemyScore > answerScore) {
					if (answer.length == 1)
						answer = new int[ARRAY_SIZE];
					for (int i = 0; i < ARRAY_SIZE; i++) {
						answer[i] = arr[i];
					}
					answerScore = myScore - enemyScore;
				}
				else if (myScore - enemyScore == answerScore) {
					boolean changeFlag = false;
					for (int i = 0; i < ARRAY_SIZE; i++) {
						if (arr[10 - i] < answer[10 - i]) break;
						else if (arr[10 - i] > answer[10 - i]) {
							changeFlag = true;
							break;
						}
					}
					if (changeFlag) {
						for (int i = 0; i < ARRAY_SIZE; i++) {
							answer[i] = arr[i];
						}
					}
				}
			}
			return answer;
		}

		for (int i = idx; i < ARRAY_SIZE; i++) {
			arr[i]++;
			answer = repetitionCombination(n, num + 1, i, arr, answer, info);
			arr[i]--;
		}
		return answer;
	}

	public static int[] solution(int n, int[] info) {
		int[] answer = { -1 };
		int[] arr = new int[ARRAY_SIZE];
		answerScore = 0;
		answer = repetitionCombination(n, 0, 0, arr, answer, info);
		System.out.println("answer : " + Arrays.toString(answer));
		return answer;
	}
}
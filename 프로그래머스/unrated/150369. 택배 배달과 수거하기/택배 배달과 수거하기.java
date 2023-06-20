import java.util.*;

class Solution {
    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        
        long answer = 0;
        /*
        배달 -> 픽업
        */
        
        int currentDeliveryIdx = -1;
        int currentPickupIdx = -1;
        
        //초기화
        for (int i = 0; i < n; i++) {
            if (deliveries[i] > 0)
                currentDeliveryIdx = i;
            if (pickups[i] > 0)
                currentPickupIdx = i;
        }
        
        //배달, 픽업할 게 없을 경우
        while (currentDeliveryIdx >= 0 || currentPickupIdx >= 0) {
            answer += Math.max(currentDeliveryIdx + 1, currentPickupIdx + 1) * 2;
            //System.out.println("=============================");
            //System.out.println("answer : " + answer);
            int deliveryCap = cap;
            int pickupCap = cap;
            //배달
            while(deliveryCap > 0 && currentDeliveryIdx >= 0) {
                //System.out.println(currentDeliveryIdx + "에 배달됨");
                //현재 위치 배달될 게 트럭에 있는 것 보다 많은 경우
                if (deliveries[currentDeliveryIdx] > deliveryCap) {
                    deliveries[currentDeliveryIdx] -= deliveryCap;
                    deliveryCap = 0;
                } else { //같거나 작은 경우
                    deliveryCap -= deliveries[currentDeliveryIdx];
                    deliveries[currentDeliveryIdx] = 0;
                    //currentDeliveryIdx 갱신
                    while (currentDeliveryIdx >= 0 && deliveries[currentDeliveryIdx] == 0) {
                        currentDeliveryIdx--;
                    }
                }
                //System.out.println("트럭에 남은 배달 상자 : " + deliveryCap);
            }
            //픽업
            while(pickupCap > 0 && currentPickupIdx >= 0) {
                //System.out.println(currentPickupIdx + "에서 픽업됨");
                //현재 위치 배달될 게 트럭에 있는 것 보다 많은 경우
                if (pickups[currentPickupIdx] > pickupCap) {
                    pickups[currentPickupIdx] -= pickupCap;
                    pickupCap = 0;
                } else { //같거나 작은 경우
                    pickupCap -= pickups[currentPickupIdx];
                    pickups[currentPickupIdx] = 0;
                    //currentDeliveryIdx 갱신
                    while (currentPickupIdx >= 0 && pickups[currentPickupIdx] == 0) {
                        currentPickupIdx--;
                    }
                }
                //System.out.println("트럭에 실을 수 있는 픽업 상자 : " + pickupCap);
            }
        }
        
        return answer;
    }
}
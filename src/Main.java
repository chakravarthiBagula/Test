import java.util.*;

class Tweet{
    int id;
    int xCoord;
    int yCoord;

    public Tweet(int id, int xCoord, int yCoord){
        this.id = id;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }
}

class FetchTweets{
    Map<Integer, Tweet> tweets;
    Map<Integer, Integer> order;
    int sno;


    public FetchTweets(){
        order = new HashMap<>();
        tweets = new HashMap<>();
        sno = 0;
    }

    public void storeTweet(int x, int y, int id){
        order.put(id, sno++);
        tweets.put(id, new Tweet(id, x, y));
    }

    public List<Tweet> fetchTweets(int x, int y, int k){
        PriorityQueue<Tweet> minHeap = new PriorityQueue<>(
                (t1, t2) -> {
                    double d1 = calculateDistance(t1, x, y);
                    double d2 = calculateDistance(t2, x, y);
                    if (Double.compare(d1, d2) != 0) {
                        return Double.compare(d2, d1);
                    } else if (t1.xCoord != t2.xCoord) {
                        return Integer.compare(t2.xCoord, t1.xCoord);
                    } else if (t1.yCoord != t2.yCoord) {
                        return Integer.compare(t2.yCoord, t1.yCoord);
                    } else {
                        return Long.compare(order.get(t2.id), order.get(t1.id));
                    }
                }
        );

        for(Tweet tweet: tweets.values()){
            minHeap.offer(tweet);
            if(minHeap.size()>k){
                minHeap.poll();
            }
        }

        List<Tweet> result = new ArrayList<>();
        while(!minHeap.isEmpty()){
            result.add(minHeap.poll());
        }
        Collections.reverse(result);
        return result;
    }

    private double calculateDistance(Tweet tweet, int x, int y){
        int dx = tweet.xCoord - x;
        int dy = tweet.yCoord - y;
        return Math.sqrt(dx*dx + dy*dy);
    }
}



public class Main{

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);


        int n = sc.nextInt();
        FetchTweets fetchTweets = new FetchTweets();

        for(int i = 0; i < n; i++){
            String q = sc.next();
            int x = sc.nextInt();
            int y = sc.nextInt();
            if(q.equals("storeTweet")){

                int id = sc.nextInt();
                fetchTweets.storeTweet(x, y, id);
            } else {
                int k = sc.nextInt();
                List<Tweet> result = fetchTweets.fetchTweets(x, y, k);
                for(Tweet tweet: result){
                    System.out.print(tweet.id + " ");
                }
                System.out.println();
            }
        }

    }
}
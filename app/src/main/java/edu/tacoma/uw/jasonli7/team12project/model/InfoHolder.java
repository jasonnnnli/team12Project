package edu.tacoma.uw.jasonli7.team12project.model;

/**
 * Team 12 Group project.
 *
 * @author Daniel Stocksett.
 *
 * @version 3rd Aug 2020.
 *
 * A class to coordinate the dummy data until back end is implemented.
 */
public class InfoHolder {
    public static class InfoPass{

        private static String mEmail;
       // private static List<Review> mReviewList;

        private static Review mReview;
        public InfoPass() {
            mEmail = "";
       //     mReviewList = new ArrayList<>();
        }
       // public static List<Review> getReviewList() {
       //     return mReviewList;
       // }

      //  public static void setReviewList(List<Review> reviewList) {
      //      if (mReviewList.size() > 1) {
      //          mReviewList.clear();
     //       }
      //      for(Review r : reviewList) {
      //          mReviewList.add(r);
      //      }
     //   }

        public String getEmail() {
            return mEmail;
        }

        public static void setEmail(String email) {
            mEmail = email;
        }
        public static Review getmReview() {
            return mReview;
        }

        public static void setmReview(Review mReview) {
            InfoPass.mReview = mReview;
        }
        public static String getmEmail() {
            return mEmail;
        }

    }

}

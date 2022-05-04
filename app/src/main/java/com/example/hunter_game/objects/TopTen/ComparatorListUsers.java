package com.example.hunter_game.objects.TopTen;

import java.util.Comparator;

public class ComparatorListUsers implements Comparator<User> {


    /**
     * Sorting in descending order
     * @param user1
     * @param user2
     * @return int
     */
    @Override
    public int compare(User user1, User user2) {
        if(user1.getScore() == user2.getScore())
            return 0;
        if(user1.getScore() < user2.getScore())
            return 1;
        return -1;
    }
}

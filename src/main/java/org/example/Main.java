package org.example;

public class Main {
    public static void main(String[] args) {
        MiniSQL miniSQL = new MiniSQL();
        miniSQL.start("INSERT BABADOO=babadoo228, kshvitaly=ne_ybivayte_pzh, grade=2, slon=40tonn");
        miniSQL.start("INSERT BABADOO=babadoo228, kshvitaly=ne_ybivayte_pzh_pzh, grade=5, slon=40tonn");
        miniSQL.start("DELETE grade=5");
    }
}
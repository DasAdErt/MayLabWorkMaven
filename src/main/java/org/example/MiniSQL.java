package org.example;

import java.util.*;

public class MiniSQL {
    private List<Map<String, Object>> rows = new ArrayList<>();

    public List<Map<String, Object>> start(String request) {
        StringBuilder function = new StringBuilder();
        StringBuilder data = new StringBuilder();
        request = request.replaceAll(" ", "");

        for (int i = 0; i < request.length(); i++) {
            if (i < 6) {
                function.append(request.charAt(i));
            } else {
                data.append(request.charAt(i));
            }
        }

        switch (function.toString()) {
            case "INSERT" -> insert(data.toString());
            case "SELECT" -> select(data.toString());
            case "DELETE" -> delete(data.toString());
            default -> System.out.println("Ne lomay programmy!");
        }
        System.out.println(rows);
        return rows;
    }

    private void insert(String data) {
        //INSERT VALUES key=data, key=data
        Map<String, Object> row = new HashMap<>();

        StringBuilder key = new StringBuilder();
        StringBuilder value = new StringBuilder();

        data = data.replaceAll("VALUES", "");
        data = data.replaceAll("'", "");

        for (int i = 0; i != data.length(); i++) {
            if (data.charAt(i) == '=') {
                i++;

                while (data.charAt(i) != ',') {
                    value.append(data.charAt(i));
                    i++;

                    if (i == data.length()) {
                        break;
                    }
                }

                i--;
            } else if (data.charAt(i) == ',') {
                try {
                    row.put(key.toString(), Integer.parseInt(value.toString()));
                } catch (NumberFormatException e) {
                    row.put(key.toString(), value.toString());
                }

                key.delete(0, 1000);
                value.delete(0, 1000);
            } else {
                key.append(data.charAt(i));
            }
        }
        try {
            row.put(key.toString(), Integer.parseInt(value.toString()));
        } catch (NumberFormatException e) {
            row.put(key.toString(), value.toString());
        }
        rows.add(row);
//        System.out.println(row);
    }

    private void select(String data) {
        data = data.replaceAll("VALUES", "");
        data = data.replaceAll("WHERE", "");
        data = data.replaceAll("'", "");

        StringBuilder key = new StringBuilder();
        StringBuilder value = new StringBuilder();
        StringBuilder comparison = new StringBuilder();

        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == '=' || data.charAt(i) == '<' || data.charAt(i) == '>') {
                comparison.append(data.charAt(i));

                i++;

                while (i < data.length()) {
                    value.append(data.charAt(i));
                    i++;
                }
                break;
            }
            key.append(data.charAt(i));
        }

        if (comparison.toString().equals("=")) {
            for (Map<String, Object> row : rows) {
                try {
                    if (row.containsKey(key.toString()) &
                            row.get(key.toString()).equals(Integer.parseInt(value.toString()))) {
                        System.out.println(row);
                    }
                } catch (NumberFormatException ea) {
                    if (row.containsKey(key.toString()) & row.get(key.toString()).equals(value.toString())) {
                        System.out.println(row);
                    }
                }
            }
        }

        if (comparison.toString().equals("<")) {
            int oldIntValue = Integer.parseInt(value.toString());
            // kshvitaly vedmaky zaplatite chekanoy monetoy
            // chekanaya moonetka = 100rub

            for (Map<String, Object> row : rows) {
                for (int i = 0; i < oldIntValue; i++) {
                    if (row.containsKey(key.toString()) & row.get(key.toString()).equals(i)) {
                        System.out.println(row);
                    }
                }
            }
        }

        if (comparison.toString().equals(">")) {
            int oldIntValue = Integer.parseInt(value.toString());
            for (Map<String, Object> row : rows) {
                for (int i = oldIntValue; i <= maxValue(row); i++) {
                    if (row.containsKey(key.toString()) & row.get(key.toString()).equals(i)) {
                        System.out.println(row);
                        break;
                    }
                    oldIntValue++;
                }
            }
        }

//            List<List<Integer>> bebra = new ArrayList<>();
//            for (int i = 0; i < 10; i++){
//                List<Integer> stroka =  new ArrayList<>();
//
//                for (int k = 0; k < 10; k++){
//                    stroka.add(0);
//                }
//                bebra.add(stroka);
//            }
//
//            for (List<Integer> stroka : bebra){
//                System.out.println(stroka);
//            }
//            prodolzhenie sledyet v...
    }

    private void delete(String data){
        data = data.replaceAll(" ", "");
        data = data.replaceAll("'", "");
        data = data.replaceAll("WHERE", "");
        data = data.replaceAll("VALUES", "");
        StringBuilder key = new StringBuilder();
        StringBuilder value = new StringBuilder();
        StringBuilder comparison = new StringBuilder();

        for (int i = 0; i != data.length(); i++) {
            if (data.charAt(i) == '=' || data.charAt(i) == '<' || data.charAt(i) == '>') {
                comparison.append(data.charAt(i));
                while (i != data.length() - 1) {
                    i++;
                    value.append(data.charAt(i));
                }
            } else {
                key.append(data.charAt(i));
            }
        }
        if (comparison.toString().equals("=")) {
            for (int i = 0; i < rows.size(); i++) {
                Map<String, Object> row = rows.get(i);
                try {
                    if (row.containsKey(key.toString()) & row.containsValue(Integer.parseInt(value.toString()))) {
                        rows.remove(i);
                    }
                } catch (NumberFormatException e) {
                    if (row.containsKey(key.toString()) & row.containsValue(value.toString())) {
                        rows.remove(i);
                    }
                }
            }
        }
        if (comparison.toString().equals("<")) {
            int oldIntValue = Integer.parseInt(value.toString());
            for (int i = 0; i < rows.size(); i++) {
                Map<String, Object> row = rows.get(i);
                for (int index = 0; index < oldIntValue; index++) {
                    if (row.containsKey(key.toString()) & row.get(key.toString()).equals(index)) {
                        rows.remove(i);
                    }
                }
            }
        }
        if (comparison.toString().equals(">")) {
            int oldIntValue;
            for (int i = 0; i < rows.size(); i++) {
                Map<String, Object> row = rows.get(i);
                oldIntValue = Integer.parseInt(value.toString());
                for (int index = oldIntValue; index < maxValue(row); index++) {
                    if (row.containsKey(key.toString()) & row.get(key.toString()).equals(oldIntValue)) {
                        rows.remove(i);
                        break;
                    }
                    oldIntValue++;
                }
            }
        }
    }

    private int maxValue(Map<String, Object> row) {
        String valve;
        int maxValue = 0;
        int oldValue = 0;

        List<Object> values = new ArrayList<>(row.values());

        for (Object value : values) {
            valve = value.toString();

            try {
                oldValue = Integer.parseInt(valve);
            } catch (NumberFormatException e) {
                //tyt nichego net, ne beyte
                // idi svoey dorogoy stalker
            }

            if (oldValue > maxValue) {
                maxValue = oldValue;
            }
        }

        return maxValue;
    }
}

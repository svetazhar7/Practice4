package com.example.practice4_mobile;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class FragmentScreenTwo extends Fragment {
    ListView listView;
    Random random;
    private static final HashMap<String, Integer> IMAGE_RESOURCE_MAP = new HashMap<>();
    //хэш-мап для подгрузки drawble картинок книг
    static {
        //сами книги
        IMAGE_RESOURCE_MAP.put("book1.png", R.drawable.book1);
        IMAGE_RESOURCE_MAP.put("book2.png", R.drawable.book2);
        IMAGE_RESOURCE_MAP.put("book3.png", R.drawable.book3);
        IMAGE_RESOURCE_MAP.put("book4.png", R.drawable.book4);
        IMAGE_RESOURCE_MAP.put("book5.png", R.drawable.book5);
    }

    public FragmentScreenTwo()
    {
        super(R.layout.screen2);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = getView().findViewById(R.id.listview);
        //обработчик нажатия на элемент ListView
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            // Получение выбранного элемента
            Item item = (Item) parent.getItemAtPosition(position);
            Toast.makeText(getContext(), "Нажатие на: " + item.getText(), Toast.LENGTH_SHORT).show();
            Log.d("FragmentScreenTwo", "Нажатие на: " + item.getText());
        });
        String [] detectiveBooks ;//массив для названий книг
        try {
            detectiveBooks = getBooksFromFile(getContext()).toArray(new String[getBooksFromFile(getContext()).size()]);
            //вызов метода считывания книг построчно из файла
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        random = new Random();//модуль для случайного выбора обложки
        ArrayList<Item> items = new ArrayList<>();//массив уже для обложек
        String[] imageNames = {"book1.png", "book2.png", "book3.png", "book4.png","book5.png"};

        for (int i = 0; i < 200; i++) {//цикл по всем 200 эл-там ListView
            int randomIndex = random.nextInt(imageNames.length);//рандомайз картинки
            String imageName = imageNames[randomIndex];
            int imageResourceId = IMAGE_RESOURCE_MAP.get(imageName);
            Item item = new Item(imageResourceId, detectiveBooks[i]);//установка картинки и названия книги
            items.add(item);
        }
        MyCustomListAdapter adapter = new MyCustomListAdapter(getContext(), R.layout.list_view, items);//создание адаптера
        listView.setAdapter(adapter);//установка адаптера

    }
    public ArrayList<String> getBooksFromFile(Context context) throws IOException//метод для чтения книг построчно из файла
    {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            String line;
            AssetManager assetManager = context.getAssets();
            InputStreamReader istream = new InputStreamReader(assetManager.open("books.txt"));
            BufferedReader in = new BufferedReader(istream);
            while ((line = in.readLine()) != null){
                arrayList.add(line);
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return arrayList;

    }
}


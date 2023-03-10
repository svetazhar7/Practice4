package com.example.practice4_mobile;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class FragmentScreenThree extends Fragment {

    private static final HashMap<String, Integer> IMAGE_RESOURCE_MAP = new HashMap<>();

    // хэш-мап для подгрузки drawble картинок книг
    static {
        // сами книги
        IMAGE_RESOURCE_MAP.put("book1.png", R.drawable.book1);
        IMAGE_RESOURCE_MAP.put("book2.png", R.drawable.book2);
        IMAGE_RESOURCE_MAP.put("book3.png", R.drawable.book3);
        IMAGE_RESOURCE_MAP.put("book4.png", R.drawable.book4);
        IMAGE_RESOURCE_MAP.put("book5.png", R.drawable.book5);
    }

    private RecyclerView recyclerView;
    private MyCustomListAdapter2 adapter;
    private ArrayList<Item> items;
    private Random random;

    public FragmentScreenThree() {
        super(R.layout.screen3);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            // Определяем слушателя касания элемента в RecyclerView
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                // Проверяем, что был сделан щелчок по элементу списка
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && e.getAction() == MotionEvent.ACTION_UP) {
                    // Определяем позицию выбранного элемента в списке
                    int position = rv.getChildAdapterPosition(child);
                    // Получаем объект Item по позиции
                    Item item = items.get(position);
                    // Показываем Toast сообщение с названием книги
                    Toast.makeText(getContext(), "Нажатие на: " + item.getText(), Toast.LENGTH_SHORT).show();
                    // Выводим сообщение в Logcat
                    Log.d("FragmentScreenThree", "Нажатие на: " + item.getText());
                    // Возвращаем true, чтобы сообщить, что касание обработано
                    return true;
                }
                // Возвращаем false, чтобы сообщить, что касание не было обработано
                return false;
            }
            //Методы onTouchEvent и onRequestDisallowInterceptTouchEvent добавлены для реализации
            // интерфейса RecyclerView.OnItemTouchListener.
            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });

        items = new ArrayList<>();// массив уже для обложек
        random = new Random();// модуль для случайного выбора обложки

        String[] fantasyBooks;// массив для названий книг
        try {
            fantasyBooks = getBooksFromFile(getContext()).toArray(new String[getBooksFromFile(getContext()).size()]);
            // вызов метода считывания книг построчно из файла
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] imageNames = {"book1.png", "book2.png", "book3.png", "book4.png", "book5.png"};

        for (int i = 0; i < 200; i++) {// цикл по всем 200 эл-там ListView
            int randomIndex = random.nextInt(imageNames.length);// рандомайз картинки
            String imageName = imageNames[randomIndex];
            int imageResourceId = IMAGE_RESOURCE_MAP.get(imageName);
            Item item = new Item(imageResourceId, fantasyBooks[i]);// установка картинки и названия книги
            items.add(item);
        }

        adapter = new MyCustomListAdapter2(items);// создание адаптера
        recyclerView.setAdapter(adapter);// установка адаптера


    }

    public ArrayList<String> getBooksFromFile(Context context) throws IOException//метод для чтения книг построчно из файла
    {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            String line;
            AssetManager assetManager = context.getAssets();
            InputStreamReader istream = new InputStreamReader(assetManager.open("books2.txt"));
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
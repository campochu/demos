package me.campochu.sectionapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.campochu.sectionapp.R;
import me.campochu.sectionapp.Section;
import me.campochu.sectionapp.SectionAdapter;
import me.campochu.sectionapp.SectionItemView;
import me.campochu.sectionapp.SectionItemView.SectionListener;
import me.campochu.sectionapp.SectionLayoutManager;
import me.campochu.sectionapp.SectionListView;
import me.campochu.sectionapp.SectionViewFactroy;
import me.campochu.sectionapp.model.Item1;
import me.campochu.sectionapp.model.Item2;
import me.campochu.sectionapp.view.Item1SectionItemView;
import me.campochu.sectionapp.view.Item2SectionItemView;

import static me.campochu.sectionapp.view.Item1SectionItemView.CLICK_HELLO;

public class MainActivity extends AppCompatActivity {

    private SectionListView mDemoList;

    private GridLayoutManager mLayoutManager;
    private SectionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDemoList = (SectionListView) findViewById(R.id.demo_list);
        mAdapter = new SectionAdapter(new SectionViewFactroy(MainActivity.this) {
            @Override
            protected SectionItemView createImpl(int section, ViewGroup parent) {
                SectionItemView sectionItemView = null;

                switch (section) {
                    case ITEM_1: {
                        sectionItemView = Item1SectionItemView.CREATOR.create(mInflater, parent);
                        sectionItemView.setSectionListener(new SectionListener<Item1>() {
                            @Override
                            public void onClickListener(int flag, Item1 model) {
                                if (flag == CLICK_HELLO) {
                                    Toast.makeText(MainActivity.this, model.getHello() + "类型 Item1 ",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        break;
                    }
                    case ITEM_2: {
                        sectionItemView = Item2SectionItemView.CREATOR.create(mInflater, parent);
                        sectionItemView.setSectionListener(new SectionListener<Item2>() {
                            @Override
                            public void onClickListener(int flag, Item2 model) {
                                if (flag == CLICK_HELLO) {
                                    Toast.makeText(MainActivity.this, model.getHello() + "类型 Item2 ",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        break;
                    }
                    default:
                        break;
                }

                return sectionItemView;
            }

            @Override
            protected int getSpan(int section) {
                return section & SPAN_MASK;
            }
        });
        mLayoutManager = new SectionLayoutManager(MainActivity.this);
        mLayoutManager.setSpanSizeLookup(new SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.getSpan(position);
            }
        });
        mDemoList.setAdapter(mAdapter);
        mDemoList.setLayoutManager(mLayoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        List<Section> sectionList = new ArrayList<>();
        sectionList.add(new Item1("哈哈"));
        sectionList.add(new Item1("嘿嘿"));
        sectionList.add(new Item1("吼吼"));
        sectionList.add(new Item2("哦哦"));
        sectionList.add(new Item2("哇哇"));
        sectionList.add(new Item1("嘎嘎"));
        mAdapter.setItems(sectionList);
    }
}

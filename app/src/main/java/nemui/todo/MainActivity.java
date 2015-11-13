package nemui.todo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private ListView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //起動時にソフトキーボードを表示しない
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(R.layout.activity_main);

        view = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();

        //Androidフレームワーク標準のレイアウト
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, list);
        view.setAdapter(adapter);

        //listView内の初期表示
        list.add("んご");
        list.add("んごごごごごごすぎ");


        /* ※※※※※※ ワンクリックでなんかできるけどとりあえず使わない ※※※※※※
        //リストの行をクリックした時のイベントリスナーを作る。
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //ここに処理を書く
            }
        });
        */

        //リストの行を長押しした時のイベントリスナーを作る。
        view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //ここに処理を書く
                list.remove(position); //listから消す
                adapter.notifyDataSetChanged(); //これがないとエラーになるっぽい
                return false;
            }
        });

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEnter();
            }
        });

        //Enterキーを押すとExitTextの入力内容を送信(+ボタンを押すのと同じ)
        EditText editText1=(EditText)findViewById(R.id.editText);
        editText1.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && keyCode == KeyEvent.KEYCODE_ENTER) {
                    onEnter();
                    return true;
                }
                return false;
            }
        });
    }

    public void onEnter() {

        EditText editText = (EditText) findViewById(R.id.editText);
        String text = editText.getText().toString();

        // add text if it is not empty
        if(StringUtils.isNotEmpty(text)){
            adapter.add(text);
        };

        InputMethodManager imm
                = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow //ソフトキーボードを隠す
                (editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        System.out.println(text);

        //入力後にEditTextの中身をclear
        editText.getText().clear();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

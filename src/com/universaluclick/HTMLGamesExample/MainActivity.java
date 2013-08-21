package com.universaluclick.HTMLGamesExample;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class MainActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Notice that setContentView() is not used, because we use the root
        // android.R.id.content as the container for each fragment

        // setup action bar for tabs
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        
        Tab tab = actionBar.newTab()
                           .setText("Crossword")
                           .setTabListener(new TabListener<HtmlGameFragment>(
                                   this, "crossword", HtmlGameFragment.class));
        actionBar.addTab(tab);
        
        tab = actionBar.newTab()
                       .setText("Sudoku")
                       .setTabListener(new TabListener<HtmlGameFragment>(
                               this, "sudoku", HtmlGameFragment.class));
        actionBar.addTab(tab);
        
        tab = actionBar.newTab()
                       .setText("Word Roundup")
                       .setTabListener(new TabListener<HtmlGameFragment>(
                               this, "wordroundup", HtmlGameFragment.class));
        actionBar.addTab(tab);
        
        tab = actionBar.newTab()
                       .setText("Up and Down Words")
                       .setTabListener(new TabListener<HtmlGameFragment>(
                               this, "upanddownwords", HtmlGameFragment.class));
        actionBar.addTab(tab);
        
        tab = actionBar.newTab()
                       .setText("PlayFour")
                       .setTabListener(new TabListener<HtmlGameFragment>(
                               this, "quickcross", HtmlGameFragment.class));
        actionBar.addTab(tab);

    }

    public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
        private Fragment mFragment;
        private final Activity mActivity;
        private final String mTag;
        private final Class<T> mClass;

        /** Constructor used each time a new tab is created.
          * @param activity  The host Activity, used to instantiate the fragment
          * @param tag  The identifier tag for the fragment
          * @param clz  The fragment's Class, used to instantiate the fragment
          */
        public TabListener(Activity activity, String tag, Class<T> clz) {
            mActivity = activity;
            mTag = tag;
            mClass = clz;
        }

        /* The following are each of the ActionBar.TabListener callbacks */

        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            // Check if the fragment is already initialized
            if (mFragment == null) {
                // If not, instantiate and add it to the activity
                mFragment = Fragment.instantiate(mActivity, mClass.getName());
                ft.add(android.R.id.content, mFragment, mTag);
            } else {
                // If it exists, simply attach it in order to show it
                ft.attach(mFragment);
            }
        }

        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            if (mFragment != null) {
                 // Detach the fragment, because another one is being attached
                ft.detach(mFragment);
            }
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            // User selected the already selected tab. Usually do nothing.
        }
    }
    
    public static class HtmlGameFragment extends Fragment {
        FragmentManager mFragmentManager = getFragmentManager();	
    	
        @SuppressLint("SetJavaScriptEnabled")
		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        	
            // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.example_fragment, container, false);
            
            // Set the WebView's HTML with game template html
        	String packageName = v.getContext().getPackageName();
        	String html = getHtmlForGameKey(getTag(), packageName);
            final String mimeType = "text/html";
            final String encoding = "UTF-8";
            WebView wv = (WebView) v.findViewById(R.id.webView1);  
            wv.getSettings().setJavaScriptEnabled(true);
            wv.loadDataWithBaseURL("", html, mimeType, encoding, "");
        	 
            return v;
        }
    
        private String getHtmlForGameKey(String gameId, String packageName) {
        	String uuClientCode = getString(R.string.UUClientCode);
        	String uuAuthorizationKey = getString(R.string.UUAuthorizationKey);
        	
            String gameTemplate = "<section class='game-wrapper' style='text-align: center;'>\n" + 
            "    <script>\n" +
            "        var tag = document.createElement('script');\n" +
            "        tag.src = 'https://embed.universaluclick.com/s.js';\n" +
            "        var aScriptTag = document.getElementsByTagName('script')[0];\n" +
            "        aScriptTag.parentNode.insertBefore(tag, aScriptTag);\n" +
            "        var apicontainer;\n" +
            "        function onUUAPIReady() {\n" +
            "            apicontainer = new UU.Player('uu_hld', {\n" +
            "                l: '" + packageName + "',\n" +
            "                height: '668',\n" +
            "                width: '768',\n" +
            "                customerId: '" + uuClientCode + "',\n" +
            "                key: '" + uuAuthorizationKey + "',\n" +
            "                gameId: '" + gameId + "'\n" +
            "            });\n" +
            "        }\n" +
            "    </script>\n" +
            "    <div id='uu_hld'></div>\n" +
            "</section>";
        
            return gameTemplate;
        }
    }

}
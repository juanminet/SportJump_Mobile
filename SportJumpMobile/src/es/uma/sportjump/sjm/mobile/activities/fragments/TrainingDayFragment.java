package es.uma.sportjump.sjm.mobile.activities.fragments;

import android.app.Activity;
import android.os.Bundle;
import roboguice.fragment.RoboFragment;

public class TrainingDayFragment extends RoboFragment{
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
//		try {
//            mListener = (OnArticleSelectedListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
//        }
	}	
	
	@Override
	/** Invoked when activity has been created */
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);    
        
//        mListItems = getResources().getStringArray(R.array.technology_names);
//        
//        setListAdapter(new ArrayAdapter<String>(getActivity(),
//                R.layout.frag_summary_textview, mListItems));
//
//        // Start with first item activated
//        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        
//
//        mListItemsUrls = getResources().getStringArray(R.array.technology_urls);
    }

}

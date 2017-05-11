package com.myth.poetrycommon.fragment;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myth.poetrycommon.BaseActivity;
import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.R;
import com.myth.poetrycommon.activity.FormerIntroActivity;
import com.myth.poetrycommon.activity.YunSearchActivity;
import com.myth.poetrycommon.db.WritingDatabaseHelper;
import com.myth.poetrycommon.entity.Former;
import com.myth.poetrycommon.entity.Writing;
import com.myth.poetrycommon.utils.CheckUtils;
import com.myth.poetrycommon.utils.ResizeUtils;
import com.myth.poetrycommon.utils.StringUtils;
import com.myth.poetrycommon.view.GCDialog;
import com.myth.poetrycommon.view.MirrorLoaderView;
import com.myth.poetrycommon.view.PasteEditText;
import com.myth.poetrycommon.view.PingzeLinearlayout;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditFragment extends Fragment {

    private LinearLayout editContent;

    private String[] sList;

    private Context mContext;

    private ArrayList<EditText> editTexts = new ArrayList<EditText>();

    private View root;

    private Former former;

    private Writing writing;

    private TextView title;

    private EditText etDesc;

    private MirrorLoaderView editContentBackground;

    private ImageView editTopBackground;

    private View keyboard;

    public EditFragment() {
    }

    public static EditFragment getInstance(Former former, Writing writing) {
        EditFragment fileViewFragment = new EditFragment();
        fileViewFragment.former = former;
        fileViewFragment.writing = writing;
        return fileViewFragment;
    }

    @Override
    public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mContext = inflater.getContext();
        root = inflater.inflate(R.layout.fragment_edit, container, false);
        initViews(root);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (StringUtils.isNumeric(writing.bgimg)) {
            int id = BaseApplication.bgimgList[Integer.parseInt(writing.bgimg)];
            editTopBackground.setImageResource(id);
            editContentBackground.setDrawableId(id);
        } else if (writing.bitmap != null) {
            root.setBackgroundDrawable(new BitmapDrawable(getResources(), writing.bitmap));
        } else {
            root.setBackgroundDrawable(new BitmapDrawable(getResources(), writing.bgimg));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        save();
    }

    public void save() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < editTexts.size(); i++) {
            if (i == editTexts.size() - 1) {
                sb.append(editTexts.get(i).getEditableText().toString());
            } else {
                sb.append(editTexts.get(i).getEditableText().toString().replaceAll("\\n", "") + "\n");
            }
        }
        writing.content = sb.toString();
        writing.title = title.getText().toString();
        writing.desc = etDesc.getText().toString();
        WritingDatabaseHelper.generateText(writing);
    }

    private void initViews(View view) {
        editTexts.clear();
        keyboard = view.findViewById(R.id.edit_keyboard);
        editContent = (LinearLayout) view.findViewById(R.id.edit_content);
        String s = former.pingze;

        if (TextUtils.isEmpty(s)) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final EditText edittext = (EditText) inflater.inflate(R.layout.edittext, null);
            edittext.setPadding(0, 30, 0, 0);
            edittext.setTypeface(BaseApplication.instance.getTypeface());
            edittext.setTextColor(getColor());
            if (writing.content != null) {
                edittext.setText(writing.content);
            }
            edittext.setBackgroundDrawable(null);
            editTexts.add(edittext);
            edittext.requestFocus();
            editContent.addView(edittext);
            edittext.setOnFocusChangeListener(etOnFocusChangeListener);
        } else {
            sList = CheckUtils.getCodeFormPingze(s.split("。"));
            if (sList != null) {
                String[] tList = null;
                if (writing.content != null) {
                    tList = writing.content.split("\n");
                }
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                for (int i = 0; i < sList.length; i++) {
                    HorizontalScrollView scrollView = new HorizontalScrollView(mContext);
                    scrollView.setHorizontalScrollBarEnabled(false);
                    View view1 = new PingzeLinearlayout(mContext, sList[i]);
                    scrollView.addView(view1);
                    view1.setPadding(0, 30, 0, 30);

                    final PasteEditText edittext = (PasteEditText) inflater
                            .inflate(R.layout.edittext, null);
                    if (i != sList.length - 1) {
                        edittext.line = i;
                        edittext.setOnPasteListener(onPasteListener);
                    }

                    edittext.setTypeface(BaseApplication.instance.getTypeface());
                    edittext.setTextColor(getColor());

                    edittext.setSingleLine();
                    edittext.setTag(i);
                    edittext.setOnFocusChangeListener(etOnFocusChangeListener);
                    editContent.addView(scrollView);
                    editContent.addView(edittext);
                    editTexts.add(edittext);

                    if (i == 0) {
                        edittext.requestFocus();
                    }

                    if (tList != null && tList.length > i) {
                        edittext.setText(tList[i]);
                    }
                }
            } else {
                Log.e("EditFragment", "sList is null");
            }
        }

        etDesc = new EditText(mContext);
        etDesc.setText(writing.desc);
        etDesc.setBackgroundDrawable(null);
        etDesc.setHint("注释");
        etDesc.setTextColor(getResources().getColor(R.color.black_light));
        editContent.addView(etDesc);
        LinearLayout.LayoutParams lps = (LinearLayout.LayoutParams) etDesc.getLayoutParams();
        lps.topMargin = ResizeUtils.getInstance().dip2px(20);

        title = (TextView) view.findViewById(R.id.edit_title);

        title.setTypeface(BaseApplication.instance.getTypeface());
        title.setTextColor(getColor());

        if (TextUtils.isEmpty(writing.title)) {
            title.setText("点击输入标题");
        } else {
            title.setText(writing.title);
        }

        title.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final EditText et = new EditText(mContext);
                et.setText(writing.title);
                et.setSelection(et.getText().length());
                new AlertDialog.Builder(mContext).setTitle(R.string.input_title).setIcon(android.R.drawable.ic_dialog_info).setView(
                        et).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        title.setText(et.getText().toString().trim());
                        BaseApplication.setDefaultUserName(et.getText().toString().trim());
                    }
                }).setNegativeButton(R.string.cancel, null).show();
            }
        });

        view.findViewById(R.id.edit_dict).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, YunSearchActivity.class);
                mContext.startActivity(intent);
            }
        });
        view.findViewById(R.id.edit_info).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FormerIntroActivity.class);
                intent.putExtra("former", former);
                startActivity(intent);
            }
        });
        final View getfocus = view.findViewById(R.id.getfocus);
        getfocus.setFocusable(true);
        getfocus.setFocusableInTouchMode(true);

        keyboard.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                hideKeyBoard();
                ((BaseActivity) mContext).setBottomVisible();
                hideSoftInputFromWindow();
                getfocus.requestFocus();
                getfocus.requestFocusFromTouch();
            }
        });

        editContentBackground = (MirrorLoaderView) view.findViewById(R.id.background_image);
        editTopBackground = (ImageView) view.findViewById(R.id.edit_top_background);
    }

    private void hideKeyBoard() {
        float translationY = keyboard.getTranslationY();
        ObjectAnimator keyboardAnimal;
        keyboardAnimal = ObjectAnimator.ofFloat(keyboard, "translationY", translationY, translationY - 300);
        keyboardAnimal.setInterpolator(new AccelerateInterpolator());
        keyboardAnimal.setDuration(200);
        keyboardAnimal.start();

        float translationX = title.getTranslationX();
        ObjectAnimator titleAnimal;
        titleAnimal = ObjectAnimator.ofFloat(title, "translationX", translationX, translationX - 100);
        titleAnimal.setInterpolator(new AccelerateInterpolator());
        titleAnimal.setDuration(200);
        titleAnimal.start();
    }

    private void showKeyBoard() {
        float translationY = keyboard.getTranslationY();
        ObjectAnimator objectAnimator;
        objectAnimator = ObjectAnimator.ofFloat(keyboard, "translationY", translationY, translationY + 300);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.setDuration(200);
        objectAnimator.start();

        float translationX = title.getTranslationX();
        ObjectAnimator titleAnimal;
        titleAnimal = ObjectAnimator.ofFloat(title, "translationX", translationX, translationX + 100);
        titleAnimal.setInterpolator(new AccelerateInterpolator());
        titleAnimal.setDuration(200);
        titleAnimal.start();
    }

    private View.OnFocusChangeListener etOnFocusChangeListener = new View.OnFocusChangeListener() {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                if (BaseApplication.getCheckAble(mContext) && sList != null) {
                    int index = (int) v.getTag();
                    CheckUtils.checkEditText((EditText) v, sList[index]);
                }
            } else {
                if (((BaseActivity) mContext).isBottomVisible()) {
                    ((BaseActivity) mContext).setBottomGone();
                    showKeyBoard();
                }
            }
        }
    };

    private void hideSoftInputFromWindow() {
        View view = ((Activity) mContext).getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private int getColor() {
        int color = BaseApplication.instance.getColorByPos(BaseApplication.getDefaultShareColor(mContext));
        return color;
    }

    private PasteEditText.OnPasteListener onPasteListener = new PasteEditText.OnPasteListener() {

        @Override
        public void onPasteClick(final int line) {
            String string = editTexts.get(line).getEditableText().toString()
                    .trim();
            final String texts[] = split(string);
            if (texts == null || texts.length < 2) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString(GCDialog.DATA_CONTENT,
                    getString(R.string.tip_paste_auto));
            bundle.putString(GCDialog.DATA_TITLE, getString(R.string.auto_save));
            bundle.putString(GCDialog.CONFIRM_TEXT, getString(R.string.save));
            bundle.putString(GCDialog.CANCEL_TEXT, getString(R.string.cancel));
            new GCDialog(getActivity(), new GCDialog.OnCustomDialogListener() {

                @Override
                public void onConfirm() {
                    int j = 0;
                    for (int i = line; j < texts.length && i < editTexts.size(); i++, j++) {
                        editTexts.get(i).setText(texts[j]);
                    }
                }

                @Override
                public void onCancel() {
                }
            }, bundle).show();
        }
    };

    private String[] split(String str) {

        /* 正则表达式：句子结束符 */
        String regEx = "：|。|！|；";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);

        /* 按照句子结束符分割句子 */
        String[] words = p.split(str);

        /* 将句子结束符连接到相应的句子后 */
        if (words.length > 0) {
            int count = 0;
            while (count < words.length) {
                if (m.find()) {
                    words[count] += m.group();
                }
                count++;
            }
        }
        return words;

    }


}

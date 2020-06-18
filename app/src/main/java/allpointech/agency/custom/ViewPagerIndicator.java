package allpointech.agency.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import allpointech.agency.R;


public class ViewPagerIndicator extends View {
    private static final int SELECTED_FACTOR = 2;
    private int SPACING_FACTOR = 4;
    private int numberOfItems = 0;
    private Paint act_paint = new Paint();
    private Paint default_paint = new Paint();
    private Paint line_paint = new Paint();
    private int act_color = Color.BLACK;
    private int default_color = Color.BLACK;
    private int line_color = Color.BLACK;
    private int activeItem = 0;
    private float radius = 4f;
    private ViewPager viewPager;

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ViewPagerIndicator,
                0, 0);

        try {
            SPACING_FACTOR = a.getInt(R.styleable.ViewPagerIndicator_spacing, 0);
            radius = a.getDimension(R.styleable.ViewPagerIndicator_radius, 0);
            default_color = a.getColor(R.styleable.ViewPagerIndicator_defaultColor, Color.BLACK);
            act_color = a.getColor(R.styleable.ViewPagerIndicator_actColor, Color.BLACK);
            line_color = a.getColor(R.styleable.ViewPagerIndicator_lineColor, Color.BLACK);
        } finally {
            a.recycle();
        }

        default_paint.setAntiAlias(true);
        default_paint.setStrokeWidth(1f);
        default_paint.setStyle(Paint.Style.STROKE);
        default_paint.setColor(default_color);
//        default_paint.setStyle(Paint.Style.FILL);
//        default_paint.setStrokeJoin(Paint.Join.ROUND);
        act_paint.setColor(act_color);
        line_paint.setColor(line_color);
        line_paint.setStyle(Paint.Style.FILL);
        line_paint.setStrokeJoin(Paint.Join.ROUND);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        float x, y;
        for (int i = 0; i < numberOfItems; i++) {
            x = (i * SELECTED_FACTOR * radius) + (i * (radius * SPACING_FACTOR)) + radius * SELECTED_FACTOR;
            y = radius * SELECTED_FACTOR;

            if (i == activeItem) {
                // 가운데 뻥
//                canvas.drawCircle(x, y, radius * SELECTED_FACTOR, act_paint);
//                canvas.drawCircle(x, y, radius, default_paint);
                canvas.drawCircle(x, y, radius, act_paint);
            } else {
                // 라인
//                canvas.drawCircle(x, y, (float) (radius * 1.2), line_paint);
                canvas.drawCircle(x, y, radius, default_paint);
            }

        }

    }


    public void setActiveItem(int activeItem) {
        this.activeItem = activeItem;
        this.invalidate();
        this.requestLayout();
    }


    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        this.numberOfItems = this.viewPager.getAdapter().getCount();
        this.setActiveItem(viewPager.getCurrentItem());
        this.viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                setActiveItem(position);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // x(n) = a + d(n-1)
        // n is numberOfItems
        int n = numberOfItems;
        int measuredWidth = (int) (radius * SELECTED_FACTOR + (radius * (SELECTED_FACTOR + SPACING_FACTOR)) * (n - 1));
        measuredWidth += radius * SELECTED_FACTOR;
        int measuredHeight = (int) (radius * SELECTED_FACTOR) * 2;
        setMeasuredDimension(measuredWidth, measuredHeight);

    }

}

package cz.tomasan7.perworldinventory.menus;

public enum MenuSize
{
    ONE(1, 9),
    TWO(2, 18),
    THREE(3, 27),
    FOUR(5, 36),
    FIVE(6, 45),
    SIX(7, 54);

    public final int size;
    public final int rows;

    MenuSize(int rows, int size)
    {
        this.size = size;
        this.rows = rows;
    }

    MenuSize getLarger ()
    {
        switch (this)
        {
            case ONE:
                return TWO;
            case TWO:
                return THREE;
            case THREE:
                return FOUR;
            case FOUR:
                return FIVE;
            case FIVE:
                return SIX;
            case SIX:
                return this;
        }

        return this;
    }
}

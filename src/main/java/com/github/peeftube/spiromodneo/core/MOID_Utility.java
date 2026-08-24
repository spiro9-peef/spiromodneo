package com.github.peeftube.spiromodneo.core;

public final class MOID_Utility
{
    private MOID_Utility() { }

    /** Use whenever you need this MOID as a raw identifier */
    public static String getMOID(MOID moid)
    { return moid.getKey().toLowerCase(); }

    /** Use whenever you need this MOID prefixed to something specific */
    public static String getMOIDSuffixed(MOID moid, String suffix)
    { return moid.getKey().toLowerCase() + "_" + suffix.toLowerCase(); }

    /** Use whenever you need this MOID as a prefix */
    public static String prefixMOID(MOID moid)
    { return moid.getKey().toLowerCase() + "_"; }

    /** Use whenever you need this MOID as a raw identifier, by an alias if one exists<p>
     * Otherwise we default to the original and hope nothing breaks */
    public static String getMOIDwithAlias(MOID moid, int index)
    { return !moid.getAlias(index).toLowerCase().equals("") ?
            moid.getAlias(index).toLowerCase() : getMOID(moid); }

    /** Use whenever you need this MOID as a prefix, by an alias if one exists<p>
     * Otherwise we default to the original and hope nothing breaks */
    public static String prefixMOIDwithAlias(MOID moid, int index)
    { return !moid.getAlias(index).toLowerCase().equals("") ?
            moid.getAlias(index).toLowerCase() + "_" : prefixMOID(moid); }
}

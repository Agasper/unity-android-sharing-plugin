using System;
using System.IO;
using UnityEngine;

public static class Share
{
#if UNITY_ANDROID && !UNITY_EDITOR
        public static void OpenShareDialog(string title, string text, Texture2D image = null)
        {
            AndroidJavaClass sharingClass = new AndroidJavaClass("net.agasper.unitysharingplugin.Sharing");
            if (image != null)
            {
                byte[] bytes = image.EncodeToPNG();
                File.WriteAllBytes(sharingClass.CallStatic<string>("GetImagePath"), bytes);
            }

            sharingClass.CallStatic("Share", title, text, image != null ? 1 : 0);
        }
#else
    public static void OpenShareDialog(string title, string text, Texture2D image = null)
    {
        Debug.LogWarning("Sharing is not available under this OS");
    }
#endif
}

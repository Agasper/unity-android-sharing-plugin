using UnityEngine;
using System.Collections;

public class UI : MonoBehaviour {

    public Texture2D sharingImage;

    public void ShareMe()
    {
        Share.OpenShareDialog("Title", "This is awesome game !!!", sharingImage);
    }
}

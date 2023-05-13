package com.example.ourproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Contact extends AppCompatActivity {
    private TextView link, oneCard, flex, git, linkTree, email, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        oneCard = findViewById(R.id.editOneCard);
        flex = findViewById(R.id.editFlex);
        git = findViewById(R.id.editReadMe);
        linkTree = findViewById(R.id.editLinkTree);
        email = findViewById(R.id.editEmail);
        address = findViewById(R.id.editAddress);


        flex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link = findViewById(R.id.editFlex);
                setLink("https://www.pace.edu/auxiliary-services/flex-and-voluntarydawg-dollars");
            }
        });

        oneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link = findViewById(R.id.editOneCard);
                setLink("https://pace-sp.transactcampus.com/eAccounts");
            }
        });

        git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link = findViewById(R.id.editReadMe);
                setLink("https://github.com/shaktisrathore/CS374-639_Final/blob/main/README.md");
            }
        });


        linkTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link = findViewById(R.id.editLinkTree);
                setLink("https://linktr.ee/thriftybites?utm_source=linktree_admin_share");
            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link = findViewById(R.id.editAddress);
                setLink("https://goo.gl/maps/r1L8VLgVmdQzPeeU7");
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:thriftybites2023@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

    }


    public void setLink(String s) {
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLink(s);
            }
        });
    }

    public void goLink(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}





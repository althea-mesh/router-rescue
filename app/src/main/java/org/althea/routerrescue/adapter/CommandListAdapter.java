/*
 * Mercury-SSH
 * Copyright (C) 2017 Skarafaz
 *
 * This file is part of Mercury-SSH.
 *
 * Mercury-SSH is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * Mercury-SSH is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Mercury-SSH.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.althea.routerrescue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.MaterialDialog;
import org.althea.routerrescue.R;
import org.althea.routerrescue.model.config.Command;
import org.althea.routerrescue.ssh.SshCommandRegular;

import java.util.List;

public class CommandListAdapter extends ArrayAdapter<Command> {

    public CommandListAdapter(Context context, List<Command> commands) {
        super(context, R.layout.command_list_item, commands);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.command_list_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Command command = getItem(position);
        holder.name.setText(command.getName());
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext())
                        .title(command.getName())
                        .content(command.getDescription())
                        .show();
            }
        });
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SshCommandRegular(command).start();
            }
        });
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.row)
        RelativeLayout row;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.info)
        ImageView info;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

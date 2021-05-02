import * as React from 'react';
import {Datagrid, Filter, FunctionField, ListProps, TextField, TextInput} from 'react-admin';
import {DownloadButton, SizeRender} from 'peacetrue-file'
import {PeaceList} from 'peacetrue-react-admin';
import {UserCreatedTimeFilter, UserCreateFields} from "peacetrue-user";

const Filters = (props: any) => (
  <Filter {...props}>
    <TextInput source="name" allowEmpty alwaysOn/>
    {UserCreatedTimeFilter}
  </Filter>
);

export const AttachmentList = (props: ListProps) => {
  console.info('AttachmentList:', props);
  return (
    <PeaceList {...props} filters={<Filters/>} exporter={false}>
      <Datagrid rowClick="show">
        <TextField source="name"/>
        <TextField source="path"/>
        <FunctionField source={'sizes'} render={SizeRender}/>
        {UserCreateFields}
        <DownloadButton/>
      </Datagrid>
    </PeaceList>
  )
};

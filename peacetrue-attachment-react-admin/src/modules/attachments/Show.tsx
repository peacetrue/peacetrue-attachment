import * as React from 'react';
import {
  DeleteButton,
  EditButton,
  FunctionField,
  ListButton,
  Show,
  ShowProps,
  SimpleShowLayout,
  TextField,
  TopToolbar
} from 'react-admin';
import {DownloadButton, SizeRender} from "peacetrue-file";
import {UserCreateFields} from "peacetrue-user";

const AttachmentActions = (props: any) => {
  const {basePath, data, resource} = props;
  if (!data) return null;
  return (
    <TopToolbar>
      <ListButton basePath={basePath} record={data}/>
      <EditButton basePath={basePath} record={data}/>
      <DownloadButton filePath={data.path}/>
      <DeleteButton basePath={basePath} record={data}/>
    </TopToolbar>
  )
};

export const AttachmentShow = (props: ShowProps) => {
  console.info('AttachmentShow:', props);
  return (
    <Show actions={<AttachmentActions/>} {...props}>
      <SimpleShowLayout>
        <TextField source="name"/>
        <TextField source="path"/>
        {/*<TextField label={'大小（字节）'} source="sizes"/>*/}
        <FunctionField source="sizes" render={SizeRender}/>
        {/*<TextField label={'状态编码'} source="stateId"/>*/}
        {UserCreateFields}
      </SimpleShowLayout>
    </Show>
  );
};

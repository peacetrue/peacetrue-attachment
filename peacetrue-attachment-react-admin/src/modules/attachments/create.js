import React from 'react';
import {Create, required, SimpleForm, TextInput,} from 'react-admin';

export const AttachmentCreate = (props) => {
    console.info('AttachmentCreate:', props);
    return (
        <Create {...props} title={`新建${props.options.label}`}>
            <SimpleForm>
                <TextInput label={'名称'} source="name" validate={required()}/>
                <TextInput label={'路径'} source="path" validate={required()}/>
                <TextInput label={'大小（字节）'} source="size" validate={required()}/>
                <TextInput label={'状态编码：临时、生效、删除'} source="stateCode" validate={required()}/>
                <TextInput label={'备注'} source="remark" validate={required()}/>
            </SimpleForm>
        </Create>
    );
};

import React from 'react';
import {Edit, required, SimpleForm, TextInput} from 'react-admin';

export const AttachmentEdit = (props) => {
    console.info('AttachmentEdit:', props);
    return (
        <Edit {...props} title={`${props.options.label}#${props.id}`}>
            <SimpleForm>
                <TextInput label={'名称'} source="name" validate={required()}/>
                <TextInput label={'路径'} source="path" validate={required()}/>
                <TextInput label={'大小（字节）'} source="size" validate={required()}/>
                <TextInput label={'状态编码：临时、生效、删除'} source="stateCode" validate={required()}/>
                <TextInput label={'备注'} source="remark" validate={required()}/>
            </SimpleForm>
        </Edit>
    );
};

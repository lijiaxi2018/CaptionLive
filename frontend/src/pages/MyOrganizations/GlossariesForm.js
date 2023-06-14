import { useState, useRef, forwardRef } from 'react';
import { usePostGlossariesMutation } from '../../services/glossary';
import { Form, Button, Schema } from 'rsuite';
import "rsuite/dist/rsuite.css";

/**
 * 
 * reference: https://codesandbox.io/s/swhwiy?file=/index.js:1703-1728
 */

const TextField = forwardRef((props, ref) => {
    const { name, label, accepter, ...rest } = props;
    return (
        <Form.Group controlId={`${name}-4`} ref={ref}>
            <Form.ControlLabel>{label}</Form.ControlLabel>
            <Form.Control name={name} accepter={accepter} {...rest}/>
        </Form.Group>
    ); 
});

const { StringType, NumberType } = Schema.Types;
const model = Schema.Model({
    source: StringType().isRequired('This field is required'),
    romanization: StringType().isRequired('This field is required'),
    term: StringType().isRequired('This field is required'),
    explanation: StringType().isRequired('This field is required'),
    category: StringType().isRequired('This field is required'),
})

const GlossariesForm = ({
    organizationId,
    onClose,
    source='',
    romanization='',
    term='',
    explanation='',
    remark='',
    category='',
    editMode=0 //0 for add glossary, 1 for update glossary
}) => {
    const formRef = useRef();
    const [formValue, setFormValue] = useState({
        organizationId: organizationId,
        source: source,
        romanization: romanization,
        term: term,
        explanation: explanation,
        remark: remark,
        category: category,
        // createdTime: new Date(),
        // lastUpdatedTime: new Date(),
    })
    const [formError, setFormError] = useState({});

    const [postGlossary] = usePostGlossariesMutation();

    const handleSubmit = () => {
        if (!formRef.current.check()) {
            /** current form is not valid */
            alert('current form not valid');
        } else {
            console.log(`submit mode: ${editMode}`);
            console.log(formValue);
            postGlossary(formValue)
            .then((response) => {
                const message = response.data.message;
                if (message == 'success') {
                    alert('词汇添加成功');
                    console.log(response.data);
                } else {
                    alert(`添加失败，错误信息：${message}`);
                }
            })
        }
        
    }
    
    return (
        <div className='glossary-form'>
          <Form
            ref={formRef}
            onChange={setFormValue}
            onCheck={setFormError}
            formValue={formValue}
            model={model}
          >
            <TextField name="source" label="出自" />
            <TextField name="romanization" label="罗马音" />
            <TextField name="term" label="原文" />
            <TextField name="explanation" label="释义" />
            <TextField name="remark" label="备注" />
            <TextField name="category" label="分类" />
          </Form>
          <div className='glossary-button-group'>
            <Button onClick={handleSubmit}>提交</Button>
            <Button onClick={onClose}>关闭</Button>
          </div>
          
        </div>
    )
}

export default GlossariesForm;